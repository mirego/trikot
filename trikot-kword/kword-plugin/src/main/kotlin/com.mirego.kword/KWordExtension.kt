package com.mirego.kword

import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property

abstract class KWordExtension {
    abstract val translationFiles: ConfigurableFileCollection

    abstract val enumClassName: Property<String>

    abstract val generatedDir: DirectoryProperty
}
