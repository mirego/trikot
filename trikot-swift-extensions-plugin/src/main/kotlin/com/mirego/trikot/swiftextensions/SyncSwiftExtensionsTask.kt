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
import java.util.jar.JarFile

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
        val requestedModules = modules.get().toSet()
        val outDir = outputDir.get().asFile

        cleanOutputDirectory(outDir)

        val jarFile = JarFile(File(javaClass.protectionDomain.codeSource.location.toURI()))
        var totalFiles = 0

        jarFile.use { jar ->
            for (entry in jar.entries()) {
                if (!entry.name.startsWith(RESOURCES_PREFIX) || !entry.name.endsWith(".swift")) continue

                val relative = entry.name.removePrefix(RESOURCES_PREFIX)
                val slash = relative.indexOf('/')
                if (slash == -1) continue

                val moduleKey = relative.substring(0, slash)
                val fileName = relative.substring(slash + 1)

                // Skip modules the consumer didn't ask for (empty = sync all)
                if (requestedModules.isNotEmpty() && moduleKey !in requestedModules) continue

                val content = jar.getInputStream(entry).bufferedReader().readText()
                val processed = content.lineSequence()
                    .filter { !IMPORT_TRIKOT.matches(it) }
                    .joinToString("\n") { it.replace(FRAMEWORK_PLACEHOLDER, framework) }

                val moduleOutDir = File(outDir, moduleKey).apply { mkdirs() }
                File(moduleOutDir, fileName).writeText(processed)
                totalFiles++
            }
        }

        logger.lifecycle("Synced $totalFiles Swift files")
    }

    private fun cleanOutputDirectory(outDir: File) {
        if (!outDir.exists()) return

        outDir.walkTopDown()
            .filter { it.isFile && it.extension == "swift" }
            .forEach { it.delete() }

        outDir.walkBottomUp()
            .filter { it.isDirectory && it != outDir && it.listFiles().isNullOrEmpty() }
            .forEach { it.delete() }
    }

    companion object {
        private const val FRAMEWORK_PLACEHOLDER = "TRIKOT_FRAMEWORK_NAME"
        private const val RESOURCES_PREFIX = "swift-extensions/"
        private val IMPORT_TRIKOT = Regex("^import Trikot\\s*$")
    }
}
