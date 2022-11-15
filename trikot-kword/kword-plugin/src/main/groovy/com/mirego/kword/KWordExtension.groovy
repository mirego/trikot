package com.mirego.kword

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.tasks.Input

class KWordExtension {
    @Input
    File translationFile
    @Input
    List<File> translationFiles
    @Input
    String enumClassName
    @Input
    File generatedDir

    private Directory projectDirectory

    KWordExtension(Project project) {
        projectDirectory = project.layout.projectDirectory
    }

    File getTranslationFile() {
        return translationFile
    }

    void translationFile(Object translationFile) {
        this.translationFile = projectDirectory.files(translationFile).singleFile
    }

    List<File> getTranslationFiles() {
        return translationFiles
    }

    void translationFiles(Object... translationFiles) {
        this.translationFiles = projectDirectory.files(translationFiles)
    }

    String getEnumClassName() {
        return enumClassName
    }

    void enumClassName(String enumClassName) {
        this.enumClassName = enumClassName
    }

    File getGeneratedDir() {
        return generatedDir
    }

    void generatedDir(Object generatedDir) {
        this.generatedDir = projectDirectory.files(generatedDir).singleFile
    }
}
