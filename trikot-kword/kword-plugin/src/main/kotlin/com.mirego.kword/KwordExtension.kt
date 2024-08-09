package com.mirego.kword

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.tasks.Input
import java.io.File

open class KWordExtension(project: Project) {
    @Input
    var translationFile: File? = null

    @Input
    var translationFiles: List<File> = emptyList()

    @Input
    var enumClassName: String? = null

    @Input
    var generatedDir: File? = null

    private val projectDirectory: Directory = project.layout.projectDirectory

    fun translationFile(translationFile: Any) {
        this.translationFile = projectDirectory.files(translationFile).singleFile
    }

    fun translationFiles(vararg translationFiles: Any) {
        this.translationFiles = projectDirectory.files(*translationFiles).toList()
    }

    fun enumClassName(enumClassName: String) {
        this.enumClassName = enumClassName
    }

    fun generatedDir(generatedDir: Any) {
        this.generatedDir = projectDirectory.files(generatedDir).singleFile
    }
}