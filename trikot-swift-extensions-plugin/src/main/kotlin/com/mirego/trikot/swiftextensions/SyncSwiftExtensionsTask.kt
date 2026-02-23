package com.mirego.trikot.swiftextensions

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.Properties

@CacheableTask
abstract class SyncSwiftExtensionsTask : DefaultTask() {

    @get:Input
    abstract val frameworkName: Property<String>

    @get:Input
    abstract val modules: ListProperty<String>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun sync() {
        val framework = frameworkName.get()
        val requestedModules = modules.get()
        val outDir = outputDir.get().asFile

        // Load manifest from classpath resources
        val manifest = loadManifest()

        // If no modules specified, sync all available modules
        val modulesToSync = if (requestedModules.isEmpty()) manifest.keys.sorted() else requestedModules

        // Clean existing .swift files in the output directory
        if (outDir.exists()) {
            outDir.walkTopDown()
                .filter { it.isFile && it.extension == "swift" }
                .forEach { it.delete() }
            // Remove empty directories left behind
            outDir.walkBottomUp()
                .filter { it.isDirectory && it != outDir && (it.listFiles()?.isEmpty() == true) }
                .forEach { it.delete() }
        }

        var totalFiles = 0

        for (moduleKey in modulesToSync) {
            val files = manifest[moduleKey]
            if (files == null) {
                logger.warn("No Swift files found for module '$moduleKey' in manifest. Available modules: ${manifest.keys.sorted()}")
                continue
            }

            val moduleOutDir = File(outDir, moduleKey)
            moduleOutDir.mkdirs()

            for (fileName in files) {
                val resourcePath = "swift-extensions/$moduleKey/$fileName"
                val inputStream = javaClass.classLoader.getResourceAsStream(resourcePath)
                if (inputStream == null) {
                    logger.warn("Resource not found: $resourcePath")
                } else {
                    val content = inputStream.bufferedReader().readText()

                    val processed = content.lineSequence()
                        .filter { line -> !line.matches(Regex("^import Trikot\\s*$")) }
                        .joinToString("\n") { line ->
                            line.replace("TRIKOT_FRAMEWORK_NAME", framework)
                        }

                    File(moduleOutDir, fileName).writeText(processed)
                    totalFiles++
                }
            }
        }

        logger.lifecycle("Synced $totalFiles Swift files for modules: $modulesToSync")
    }

    private fun loadManifest(): Map<String, List<String>> {
        val stream = javaClass.classLoader.getResourceAsStream("swift-extensions/manifest.properties")
            ?: error("Could not find swift-extensions/manifest.properties in plugin resources. Is the plugin JAR built correctly?")

        val props = Properties()
        props.load(stream)

        return props.entries.associate { (key, value) ->
            key.toString() to value.toString().split(",").filter { it.isNotBlank() }
        }
    }
}
