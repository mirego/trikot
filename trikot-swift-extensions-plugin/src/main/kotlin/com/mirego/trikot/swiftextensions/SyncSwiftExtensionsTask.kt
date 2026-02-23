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
        val manifest = discoverModules()
        val modulesToSync = modules.get().ifEmpty { manifest.keys.sorted() }
        val outDir = outputDir.get().asFile

        cleanOutputDirectory(outDir)

        var totalFiles = 0

        for (moduleKey in modulesToSync) {
            val files = manifest[moduleKey]
            if (files == null) {
                logger.warn("Unknown module '$moduleKey'. Available: ${manifest.keys.sorted()}")
                continue
            }

            val moduleOutDir = File(outDir, moduleKey).apply { mkdirs() }

            for (fileName in files) {
                val content = readResource("$RESOURCES_PREFIX$moduleKey/$fileName")
                if (content == null) {
                    logger.warn("Resource not found: $RESOURCES_PREFIX$moduleKey/$fileName")
                    continue
                }

                val processed = content.lineSequence()
                    .filter { !IMPORT_TRIKOT.matches(it) }
                    .joinToString("\n") { it.replace(FRAMEWORK_PLACEHOLDER, framework) }

                File(moduleOutDir, fileName).writeText(processed)
                totalFiles++
            }
        }

        logger.lifecycle("Synced $totalFiles Swift files for modules: $modulesToSync")
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

    private fun readResource(path: String): String? =
        javaClass.classLoader.getResourceAsStream(path)?.bufferedReader()?.readText()

    /**
     * Scan the plugin JAR to discover available modules and their Swift files.
     * The JAR contains entries like `swift-extensions/<module>/<file>.swift`.
     */
    private fun discoverModules(): Map<String, List<String>> {
        val jarFile = JarFile(File(javaClass.protectionDomain.codeSource.location.toURI()))
        return jarFile.use { jar ->
            jar.entries().asSequence()
                .map { it.name }
                .filter { it.startsWith(RESOURCES_PREFIX) && it.endsWith(".swift") }
                .map { path ->
                    val relative = path.removePrefix(RESOURCES_PREFIX)
                    val slash = relative.indexOf('/')
                    relative.substring(0, slash) to relative.substring(slash + 1)
                }
                .groupBy({ it.first }, { it.second })
                .mapValues { (_, files) -> files.sorted() }
        }
    }

    companion object {
        private const val FRAMEWORK_PLACEHOLDER = "TRIKOT_FRAMEWORK_NAME"
        private const val RESOURCES_PREFIX = "swift-extensions/"
        private val IMPORT_TRIKOT = Regex("^import Trikot\\s*$")
    }
}
