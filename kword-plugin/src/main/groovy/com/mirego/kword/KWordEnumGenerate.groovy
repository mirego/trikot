package com.mirego.kword

import com.squareup.kotlinpoet.*
import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class KWordEnumGenerate extends DefaultTask {
    private static final ClassName KOTLIN_STRING = ClassName.bestGuess('kotlin.String')

    @Lazy
    KWordExtension extension = { project.extensions.getByType(KWordExtension) }()

    @TaskAction
    void generate() {
        ClassName generatedClassName = ClassName.bestGuess(getEnumClassName())
        TypeSpec.Builder enumBuilder = enumBuilder(generatedClassName)
        addEnumConstants(enumBuilder)
        writeFile(generatedClassName, enumBuilder)
    }

    private TypeSpec.Builder enumBuilder(ClassName generatedClassName) {
        TypeSpec.enumBuilder(generatedClassName)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("translationKey", KOTLIN_STRING, KModifier.OVERRIDE)
                    .build())
            .addProperty(
                PropertySpec.builder("translationKey", KOTLIN_STRING)
                    .initializer("translationKey")
                    .build())
            .addSuperinterface(ClassName.bestGuess('com.mirego.trikot.kword.KWordKey'), CodeBlock.EMPTY)
    }

    private void addEnumConstants(enumBuilder) {
        parseKeys()
            .collect { key -> new Tuple2<String, String>(underscoreKey(key), key) }
            .each {
                enumBuilder.addEnumConstant(it.first, TypeSpec.anonymousClassBuilder()
                    .addSuperclassConstructorParameter("%S", it.second)
                    .build())
            }
    }

    private writeFile(ClassName generatedClassName, TypeSpec.Builder enumBuilder) {
        FileSpec.builder(generatedClassName.packageName, generatedClassName.simpleName)
            .addType(enumBuilder.build())
            .build()
            .writeTo(getGeneratedDir())
    }

    private List<String> parseKeys() {
        final Set<String> keys = new HashSet<>()
        getTranslationFiles().collect {
            Map<String, Object> translations = new JsonSlurper().parse(it) as Map<String, Object>
            keys.addAll(translations.keySet())
        }
        keys.toList()
    }

    static String underscoreKey(String key) {
        key.replaceAll(/([A-Z])/, '_$1').replaceAll(/\./, '_').toUpperCase()
    }

    List<File> getTranslationFiles() {
        return extension.translationFile != null ? Arrays.asList(extension.translationFile) : extension.translationFiles
    }

    String getEnumClassName() {
        return extension.enumClassName
    }

    File getGeneratedDir() {
        return extension.generatedDir
    }
}
