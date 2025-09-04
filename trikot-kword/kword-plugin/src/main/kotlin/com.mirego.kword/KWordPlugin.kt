package com.mirego.kword

import org.gradle.api.Plugin
import org.gradle.api.Project

class KWordPlugin : Plugin<Project> {
    private companion object {
        private const val TASKS_GROUP = "KWord"
    }

    override fun apply(project: Project) {
        val extension = project.extensions.create("kword", KWordExtension::class.java)

        project.tasks.create("kwordGenerateConst", KWordConstGenerate::class.java) {
            it.group = TASKS_GROUP
            it.description = "Generate keys const based on json translation file."
            it.targetClassName.set(extension.targetClassName)
            it.translationFiles.setFrom(extension.translationFiles)
            it.generatedDir.set(extension.generatedDir)
        }
    }
}
