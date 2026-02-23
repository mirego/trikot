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

val swiftModules = buildMap<String, String> {
    rootProject.projectDir.listFiles()
        ?.filter { it.isDirectory && it.name.startsWith("trikot-") }
        ?.sorted()
        ?.forEach { trikotDir ->
            val swiftExtDir = File(trikotDir, "swift-extensions")
            if (swiftExtDir.isDirectory) {
                val moduleKey = trikotDir.name.removePrefix("trikot-")

                // Root-level .swift files → module key "<name>"
                if (swiftExtDir.listFiles()?.any { it.isFile && it.extension == "swift" } == true) {
                    put(moduleKey, "${trikotDir.name}/swift-extensions")
                }

                // Sub-directories → module key "<name>-<sub>"
                swiftExtDir.listFiles()
                    ?.filter { it.isDirectory }
                    ?.sorted()
                    ?.forEach { subDir ->
                        if (subDir.listFiles()?.any { it.isFile && it.extension == "swift" } == true) {
                            put("$moduleKey-${subDir.name}", "${trikotDir.name}/swift-extensions/${subDir.name}")
                        }
                    }
            }
        }
}

tasks.named<Copy>("processResources") {
    val manifestEntries = mutableMapOf<String, List<String>>()

    swiftModules.forEach { (moduleKey, sourcePath) ->
        val sourceDir = rootProject.file(sourcePath)
        from(sourceDir) {
            include("*.swift")
            into("swift-extensions/$moduleKey")
        }

        val swiftFiles = sourceDir.listFiles()
            ?.filter { it.isFile && it.extension == "swift" }
            ?.map { it.name }
            ?.sorted()
            ?: emptyList()
        if (swiftFiles.isNotEmpty()) {
            manifestEntries[moduleKey] = swiftFiles
        }
    }

    doFirst {
        val manifestFile = File(temporaryDir, "swift-extensions/manifest.properties")
        manifestFile.parentFile.mkdirs()
        val content = manifestEntries.entries
            .sortedBy { it.key }
            .joinToString("\n") { (key, files) ->
                "$key=${files.joinToString(",")}"
            }
        manifestFile.writeText(content)
    }
    from(temporaryDir) {
        include("swift-extensions/manifest.properties")
    }
}

publishing {
    publications {
        create<MavenPublication>("binaryAndSources") {
            from(components["java"])
        }
    }
}
