plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("mirego.publish")
}

group = "com.mirego.trikot"

gradlePlugin {
    plugins {
        create("trikotSwiftExtensions") {
            id = "com.mirego.trikot.swift-extensions"
            implementationClass = "com.mirego.trikot.swiftextensions.TrikotSwiftExtensionsPlugin"
        }
    }
}

data class SwiftModule(val key: String, val sourcePath: String, val isSubmodule: Boolean)

val swiftModules: List<SwiftModule> = rootProject.projectDir.listFiles()
    .orEmpty()
    .filter { it.isDirectory && it.name.startsWith("trikot-") }
    .sorted()
    .flatMap { trikotDir ->
        val moduleKey = trikotDir.name.removePrefix("trikot-")
        val swiftExtDir = File(trikotDir, "swift-extensions")
        if (!swiftExtDir.isDirectory) return@flatMap emptyList()

        buildList {
            if (swiftExtDir.hasDirectSwiftFiles()) {
                add(SwiftModule(moduleKey, "${trikotDir.name}/swift-extensions", isSubmodule = false))
            }
            swiftExtDir.listFiles().orEmpty()
                .filter { it.isDirectory && it.hasSwiftFilesRecursively() }
                .sorted()
                .forEach { subDir ->
                    add(SwiftModule("$moduleKey-${subDir.name}", "${trikotDir.name}/swift-extensions/${subDir.name}", isSubmodule = true))
                }
        }
    }

fun File.hasDirectSwiftFiles(): Boolean = listFiles().orEmpty().any { it.isFile && it.extension == "swift" }
fun File.hasSwiftFilesRecursively(): Boolean = walkTopDown().any { it.isFile && it.extension == "swift" }

tasks.named<Copy>("processResources") {
    swiftModules.forEach { module ->
        from(rootProject.file(module.sourcePath)) {
            include(if (module.isSubmodule) "**/*.swift" else "*.swift")
            into("swift-extensions/${module.key}")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("binaryAndSources") {
            from(components["java"])
        }
    }
}
