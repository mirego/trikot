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

abstract class KWordConstGenerate : DefaultTask() {

    @get:InputFiles
    abstract val translationFiles: ConfigurableFileCollection

    @get:Input
    abstract val targetClassName: Property<String> // Renamed from enumClassName

    @get:OutputDirectory
    abstract val generatedDir: DirectoryProperty

    @get:OutputFile
    val generatedClassFile: Provider<RegularFile>
        get() =
            generatedDir.file(targetClassName.map { it.replace(".", "/") + ".kt" }) // Updated to use targetClassName

    @TaskAction
    fun generate() {
        logger.warn("Generating KWord consts for ${targetClassName.get()}") // Updated to use targetClassName
        logger.warn("Translation files: ${translationFiles.files}")
        val generatedClassName = ClassName.bestGuess(targetClassName.get()) // Updated to use targetClassName
        val kwordKeyInterface = ClassName("com.mirego.trikot.kword", "KWordKey")

        val constructorSpec = FunSpec.constructorBuilder()
            .addParameter("translationKey", String::class)
            .build()

        val translationKeyProperty = PropertySpec.builder("translationKey", String::class, KModifier.OVERRIDE)
            .initializer("translationKey")
            .build()

        val keyProperty = PropertySpec.builder("name", String::class)
            .getter(FunSpec.getterBuilder().addStatement("return translationKey").build())
            .build()

        val classBuilder = TypeSpec.classBuilder(generatedClassName)
            .addModifiers(KModifier.DATA)
            .addSuperinterface(kwordKeyInterface)
            .primaryConstructor(constructorSpec)
            .addProperty(translationKeyProperty)
            .addProperty(keyProperty)

        val companionObjectBuilder = TypeSpec.companionObjectBuilder()

        companionObjectBuilder.addFunction(
            FunSpec.builder("valueOf")
                .addParameter("translationKey", String::class)
                .returns(kwordKeyInterface)
                .addStatement("return %T(translationKey)", generatedClassName)
                .build()
        )

        parseKeys()
            .map { key -> underscoreKey(key) to key }
            .forEach { (constName, translationKeyValue) ->
                companionObjectBuilder.addProperty(
                    PropertySpec.builder(constName, generatedClassName)
                        .initializer("%T(%S)", generatedClassName, translationKeyValue)
                        .build()
                )
            }

        classBuilder.addType(companionObjectBuilder.build())

        writeFile(generatedClassName, classBuilder)
    }

    private fun writeFile(generatedClassName: ClassName, classBuilder: TypeSpec.Builder) {
        FileSpec.builder(generatedClassName.packageName, generatedClassName.simpleName)
            .indent("    ")
            .addType(classBuilder.build())
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

