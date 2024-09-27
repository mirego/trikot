package com.mirego.kword

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.util.Locale

abstract class KWordEnumGenerate : DefaultTask() {

    @get:InputFiles
    abstract val translationFiles: ConfigurableFileCollection

    @get:Input
    abstract val enumClassName: Property<String>

    @get:OutputDirectory
    abstract val generatedDir: DirectoryProperty

    @get:OutputFile
    val generatedClassFile: Provider<RegularFile> get() =
        generatedDir.file(enumClassName.map { it.replace(".", "/") + ".kt" })

    @TaskAction
    fun generate() {
        logger.warn("Generating KWord enum for ${enumClassName.get()}")
        logger.warn("Translation files: ${translationFiles.files}")
        val generatedClassName = ClassName.bestGuess(enumClassName.get())
        val enumBuilder = enumBuilder(generatedClassName)
        addEnumConstants(enumBuilder)
        writeFile(generatedClassName, enumBuilder)
    }

    private fun enumBuilder(generatedClassName: ClassName): TypeSpec.Builder {
        return TypeSpec.enumBuilder(generatedClassName)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("translationKey", String::class)
                    .build()
            )
            .addProperty(
                PropertySpec.builder("translationKey", String::class, KModifier.OVERRIDE)
                    .initializer("translationKey")
                    .build()
            )
            .addSuperinterface(ClassName.bestGuess("com.mirego.trikot.kword.KWordKey"))
    }

    private fun addEnumConstants(enumBuilder: TypeSpec.Builder) {
        parseKeys()
            .map { key -> underscoreKey(key) to key }
            .forEach {
                enumBuilder.addEnumConstant(
                    it.first,
                    TypeSpec.anonymousClassBuilder()
                        .addSuperclassConstructorParameter("%S", it.second)
                        .build()
                )
            }
    }

    private fun writeFile(generatedClassName: ClassName, enumBuilder: TypeSpec.Builder) {
        FileSpec.builder(generatedClassName.packageName, generatedClassName.simpleName)
            .indent("    ")
            .addType(enumBuilder.build())
            .build()
            .writeTo(generatedDir.get().asFile)
    }

    private fun parseKeys(): List<String> {
        val keys = mutableSetOf<String>()
        translationFiles.forEach {
            val translations = JsonSlurper().parse(it) as Map<String, Any>
            keys.addAll(translations.keys)
        }
        return keys.toList()
    }

    companion object {
        fun underscoreKey(key: String): String {
            return key.replace(Regex("([A-Z])"), "_$1").replace(".", "_").uppercase(Locale.ENGLISH)
        }
    }
}
