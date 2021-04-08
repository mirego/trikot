package com.mirego.kword

import org.gradle.api.Project
import org.gradle.api.tasks.Input

class KWordExtension {
    private Project project

    @Input
    File translationFile
    @Input
    List<File> translationFiles
    @Input
    String enumClassName
    @Input
    File generatedDir

    KWordExtension(Project project) {
        this.project = project
    }

    File getTranslationFile() {
        return translationFile
    }

    void translationFile(Object translationFile) {
        this.translationFile = project.file(translationFile)
    }

    List<File> getTranslationFiles() {
        return translationFiles
    }

    void translationFiles(Object... translationFiles) {
        this.translationFiles = project.files(translationFiles).toList()
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
        this.generatedDir = project.file(generatedDir)
    }
}
