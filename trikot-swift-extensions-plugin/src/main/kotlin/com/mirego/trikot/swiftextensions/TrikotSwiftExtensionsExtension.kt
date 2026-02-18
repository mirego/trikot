package com.mirego.trikot.swiftextensions

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class TrikotSwiftExtensionsExtension @Inject constructor(objects: ObjectFactory) {
    val frameworkName: Property<String> = objects.property(String::class.java)
    val outputDir: DirectoryProperty = objects.directoryProperty()
    val modules: ListProperty<String> = objects.listProperty(String::class.java).convention(
        listOf("streams", "viewmodels", "viewmodels-kingfisher", "http", "kword")
    )
}
