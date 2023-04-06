package com.mirego.trikot.viewmodels.declarative.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSTypeReference
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import kotlin.reflect.KClass

abstract class BaseVMDCodeGenerator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {
    abstract val viewModelType: KClass<*>

    abstract val defaultSuperClass: TypeName

    abstract val lifecycleComponentName: String

    abstract val lifecycleComponentType: KClass<*>

    abstract val publishedPropertyType: KClass<*>

    abstract val publisherType: KClass<*>

    abstract val publisherName: String

    abstract fun CodeBlock.Builder.addPublishedMemberInvocationStatement(propertyName: String): CodeBlock.Builder

    abstract fun CodeBlock.Builder.addUpdatePropertyInvocationStatement(propertyName: String, value: String): CodeBlock.Builder

    fun generateViewModelParents(viewModelMetaDataList: List<ViewModelMetaData>) {
        logger.logging("Generating abstract parents for viewmodels: $viewModelMetaDataList")
        viewModelMetaDataList.forEach {
            try {
                generateAbstractParent(it)
            } catch (e: Exception) {
                throw Exception("Unable to generate class with metadata = $it", e)
            }
        }
    }

    private fun generateAbstractParent(viewModelMetaData: ViewModelMetaData) {
        logger.logging("$viewModelMetaData")

        val isConcrete = viewModelMetaData.allFieldsArePublished && !viewModelMetaData.hasAbstractMethods

        val prefix = when {
            isConcrete -> "Default"
            else -> "Base"
        }
        val classModifier = when {
            isConcrete -> KModifier.OPEN
            else -> KModifier.ABSTRACT
        }

        val className = "$prefix${viewModelMetaData.viewModelInterface.simpleName.asString()}Impl"
        val packageName = viewModelMetaData.viewModelInterface.packageName.asString()

        val fileSpec = FileSpec.builder(packageName, className)
            .addType(
                TypeSpec.classBuilder(className)
                    .addModifiers(classModifier)
                    .addSuperinterface(viewModelMetaData.viewModelInterface.toClassName())
                    .superclass(generateSuperClass(viewModelMetaData))
                    .primaryConstructor(generateConstructor(viewModelMetaData))
                    .addSuperclassConstructorParameter(generateConstructorParameters(viewModelMetaData))
                    .addProperties(generatePropertyDelegates(viewModelMetaData))
                    .addProperties(generatePropertyOverrides(viewModelMetaData))
                    .addProperty(generatePropertyMappingOverride(viewModelMetaData))
                    .addFunctions(generateBindFunctions(viewModelMetaData))
                    .addFunction(generateBindAllFunction(viewModelMetaData))
                    .addFunctions(generatePublisherForPropertyFunctions(viewModelMetaData))
                    .build()
            )
            .build()

        logger.logging("Result: \n$fileSpec")
        logger.logging("Creating file:")
        logger.logging("     packageName: $packageName")
        logger.logging("     className: $className")

        OutputStreamWriter(
            codeGenerator.createNewFile(
                dependencies = Dependencies(false),
                packageName = packageName,
                fileName = className
            ),
            StandardCharsets.UTF_8
        ).use(fileSpec::writeTo)
    }

    private fun generateSuperClass(viewModelMetaData: ViewModelMetaData): TypeName =
        viewModelMetaData.superClass?.toClassName() ?: defaultSuperClass

    private fun generateConstructor(viewModelMetaData: ViewModelMetaData): FunSpec {
        val builder = FunSpec.constructorBuilder()
            .addParameters(
                viewModelMetaData.publishedProperty
                    .map { propertyDeclaration ->
                        validateType(propertyDeclaration.type)
                        val resolvedType = propertyDeclaration.type.resolve()
                        val builder = ParameterSpec.builder(
                            name = "${propertyDeclaration.simpleName.getShortName()}InitialValue",
                            type = resolvedType.toTypeName()
                        )
                        if (resolvedType.isMarkedNullable) {
                            builder.defaultValue("null")
                        }
                        builder.build()
                    }
            )

        viewModelMetaData.superClass?.primaryConstructor?.parameters?.let { parameters ->
            parameters.forEach { parameter ->
                validateType(parameter.type)
                builder.addParameter(parameter.name?.asString() ?: "", parameter.type.toTypeName())
            }
        } ?: run {
            builder.addParameter(lifecycleComponentName, lifecycleComponentType)
        }

        return builder.build()
    }

    private fun generateConstructorParameters(viewModelMetaData: ViewModelMetaData): CodeBlock =
        viewModelMetaData.superClass?.primaryConstructor?.parameters?.let { parameters ->
            CodeBlock.of(parameters.joinToString(", "))
        } ?: run {
            CodeBlock.of(lifecycleComponentName)
        }

    private fun generatePropertyOverrides(viewModelMetaData: ViewModelMetaData) =
        viewModelMetaData.publishedProperty
            .map { propertyDeclaration ->
                validateType(propertyDeclaration.type)
                PropertySpec.builder(
                    name = propertyDeclaration.simpleName.getShortName(),
                    type = propertyDeclaration.type.resolve().toTypeName()
                )
                    .mutable()
                    .addModifiers(KModifier.OVERRIDE)
                    .delegate(
                        CodeBlock.builder()
                            .addStatement("${propertyDeclaration.simpleName.getShortName()}Delegate")
                            .build()
                    )
                    .build()
            }

    private fun generatePropertyDelegates(viewModelMetaData: ViewModelMetaData) =
        viewModelMetaData.publishedProperty
            .map { propertyDeclaration ->
                validateType(propertyDeclaration.type)
                PropertySpec.builder(
                    name = "${propertyDeclaration.simpleName.getShortName()}Delegate",
                    type = publishedPropertyType.asTypeName().parameterizedBy(propertyDeclaration.type.resolve().toTypeName())
                )
                    .addModifiers(KModifier.PRIVATE)
                    .delegate(
                        CodeBlock.builder()
                            .beginControlFlow("lazy")
                            .addPublishedMemberInvocationStatement(propertyDeclaration.simpleName.getShortName())
                            .endControlFlow()
                            .build()
                    )
                    .build()
            }

    private fun generatePropertyMappingOverride(viewModelMetaData: ViewModelMetaData) = PropertySpec.builder(
        name = "propertyMapping",
        type = Map::class.asClassName().parameterizedBy(
            String::class.asClassName(),
            publishedPropertyType.asClassName().parameterizedBy(STAR)
        )
    ).addModifiers(KModifier.OVERRIDE)
        .delegate(
            CodeBlock.builder()
                .beginControlFlow("lazy", LazyThreadSafetyMode::class.asTypeName())
                .add(
                    CodeBlock.builder()
                        .beginControlFlow("super.propertyMapping.toMutableMap().also")
                        .apply {
                            viewModelMetaData.publishedProperty.forEach { propertyDeclaration ->
                                add("it[::${propertyDeclaration.simpleName.getShortName()}.name] = ${propertyDeclaration.simpleName.getShortName()}Delegate\n")
                            }
                        }
                        .endControlFlow()
                        .build()
                )
                .endControlFlow()
                .build()
        )
        .build()

    private fun generateBindFunctions(viewModelMetaData: ViewModelMetaData) = viewModelMetaData.publishedProperty.map { propertyDeclaration ->
        validateType(propertyDeclaration.type)
        FunSpec
            .builder("bind${propertyDeclaration.simpleName.getShortName().replaceFirstChar { it.uppercase() }}")
            .addParameter(
                name = publisherName,
                type = publisherType.asTypeName().parameterizedBy(propertyDeclaration.type.resolve().toTypeName())
            )
            .addCode(
                CodeBlock.builder()
                    .addUpdatePropertyInvocationStatement(
                        propertyName = propertyDeclaration.simpleName.getShortName(),
                        value = publisherName
                    )
                    .build()
            )
            .build()
    }

    private fun generateBindAllFunction(viewModelMetaData: ViewModelMetaData): FunSpec {
        val bodyStatements = CodeBlock.builder()
        viewModelMetaData.publishedProperty.forEach { propertyDeclaration ->
            bodyStatements.addUpdatePropertyInvocationStatement(
                propertyName = propertyDeclaration.simpleName.getShortName(),
                value = propertyDeclaration.simpleName.getShortName()
            )
        }
        return FunSpec.builder("bind")
            .addParameters(
                viewModelMetaData.publishedProperty.map { propertyDeclaration ->
                    validateType(propertyDeclaration.type)
                    ParameterSpec(
                        name = propertyDeclaration.simpleName.getShortName(),
                        type = publisherType.asTypeName().parameterizedBy(propertyDeclaration.type.resolve().toTypeName())
                    )
                }
            )
            .addCode(bodyStatements.build())
            .build()
    }

    private fun generatePublisherForPropertyFunctions(viewModelMetaData: ViewModelMetaData) = viewModelMetaData.publishedProperty.map { propertyDeclaration ->
        validateType(propertyDeclaration.type)
        FunSpec
            .builder("${publisherName}For${propertyDeclaration.simpleName.getShortName().replaceFirstChar { it.uppercase() }}")
            .returns(returnType = publisherType.asTypeName().parameterizedBy(propertyDeclaration.type.resolve().toTypeName()))
            .addCode(
                CodeBlock.builder()
                    .add("return ${publisherName}ForProperty(::${propertyDeclaration.simpleName.getShortName()})")
                    .build()
            )
            .build()
    }

    private fun validateType(type: KSTypeReference) {
        try {
            type.resolve().toTypeName()
        } catch (e: Exception) {
            throw IllegalArgumentException("Unresolved type: $type", e)
        }
    }
}
