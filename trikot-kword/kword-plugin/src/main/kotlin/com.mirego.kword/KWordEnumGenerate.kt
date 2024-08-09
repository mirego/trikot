package com.mirego.kword

import com.squareup.kotlinpoet.*
import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.Locale

open class KWordEnumGenerate : DefaultTask() {
    @Internal
    val extension: KWordExtension = project.extensions.getByType(KWordExtension::class.java)

    @TaskAction
    fun generate() {
        val generatedClassName = ClassName.bestGuess(getEnumClassName())
        val enumBuilder = enumBuilder(generatedClassName)
        addEnumConstants(enumBuilder)
        writeFile(generatedClassName, enumBuilder)
    }

    private fun enumBuilder(generatedClassName: ClassName): TypeSpec.Builder {
        return TypeSpec.enumBuilder(generatedClassName)
            .addSuperinterface(ClassName.bestGuess("com.mirego.trikot.kword.KWordKey"))
    }

    private fun addEnumConstants(enumBuilder: TypeSpec.Builder) {
        parseKeys()
            .map { key -> underscoreKey(key) to key }
            .forEach {
                enumBuilder.addEnumConstant(it.first, TypeSpec.anonymousClassBuilder()
                    .addProperty(
                        PropertySpec.builder("translationKey", String::class, KModifier.OVERRIDE)
                            .initializer("%S", it.second)
                            .build()
                    )
                    .build())
            }
    }

    private fun writeFile(generatedClassName: ClassName, enumBuilder: TypeSpec.Builder) {
        FileSpec.builder(generatedClassName.packageName, generatedClassName.simpleName)
            .indent("    ")
            .addType(enumBuilder.build())
            .build()
            .writeTo(getGeneratedDir())
    }

    private fun parseKeys(): List<String> {
        val keys = mutableSetOf<String>()
        getTranslationFiles().forEach {
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

    @InputFiles
    fun getTranslationFiles(): List<File> =
        extension.translationFile?.let { listOf(it) } ?: extension.translationFiles

    @Internal
    fun getEnumClassName(): String {
        return extension.enumClassName ?: ""
    }

    @Internal
    fun getGeneratedDir(): File {
        return extension.generatedDir ?: File("")
    }

    @OutputFile
    fun getGeneratedClassFile(): File {
        return File(getGeneratedDir(), getEnumClassName().replace(".", "/") + ".kt")
    }
}