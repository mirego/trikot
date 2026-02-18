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

val swiftModules = mapOf(
    "streams" to "trikot-streams/swift-extensions",
    "streams-combine" to "trikot-streams/swift-extensions/combine",
    "viewmodels" to "trikot-viewmodels/swift-extensions",
    "viewmodels-kingfisher" to "trikot-viewmodels/swift-extensions/kingfisher",
    "http" to "trikot-http/swift-extensions",
    "kword" to "trikot-kword/swift-extensions",
    "bluetooth" to "trikot-bluetooth/swift-extensions",
    "analytics-firebase" to "trikot-analytics/swift-extensions/firebase",
    "analytics-mixpanel" to "trikot-analytics/swift-extensions/mixpanel",
)

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
