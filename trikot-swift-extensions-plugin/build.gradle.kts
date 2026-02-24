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

val swiftModules: Map<String, String> = rootProject.projectDir.listFiles()
    .orEmpty()
    .filter { it.isDirectory && it.name.startsWith("trikot-") }
    .sorted()
    .flatMap { trikotDir ->
        val moduleKey = trikotDir.name.removePrefix("trikot-")
        val swiftExtDir = File(trikotDir, "swift-extensions")
        if (!swiftExtDir.isDirectory) return@flatMap emptyList()

        buildList {
            if (swiftExtDir.hasSwiftFiles()) {
                add(moduleKey to "${trikotDir.name}/swift-extensions")
            }
            swiftExtDir.listFiles().orEmpty()
                .filter { it.isDirectory && it.hasSwiftFiles() }
                .sorted()
                .forEach { subDir ->
                    add("$moduleKey-${subDir.name}" to "${trikotDir.name}/swift-extensions/${subDir.name}")
                }
        }
    }
    .toMap()

fun File.hasSwiftFiles(): Boolean = listFiles().orEmpty().any { it.isFile && it.extension == "swift" }

tasks.named<Copy>("processResources") {
    swiftModules.forEach { (moduleKey, sourcePath) ->
        from(rootProject.file(sourcePath)) {
            include("*.swift")
            into("swift-extensions/$moduleKey")
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
