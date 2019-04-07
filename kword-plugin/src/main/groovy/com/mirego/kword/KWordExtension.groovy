package com.mirego.kword

import org.gradle.api.Project

class KWordExtension {
    private Project project

    File translationFile
    String enumClassName
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
