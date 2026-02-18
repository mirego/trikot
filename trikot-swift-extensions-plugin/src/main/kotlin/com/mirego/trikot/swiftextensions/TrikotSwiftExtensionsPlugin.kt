package com.mirego.trikot.swiftextensions

import org.gradle.api.Plugin
import org.gradle.api.Project

class TrikotSwiftExtensionsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "trikotSwiftExtensions",
            TrikotSwiftExtensionsExtension::class.java
        )

        project.tasks.register("syncTrikotSwiftExtensions", SyncSwiftExtensionsTask::class.java) {
            frameworkName.set(extension.frameworkName)
            modules.set(extension.modules)
            outputDir.set(extension.outputDir)
        }
    }
}
