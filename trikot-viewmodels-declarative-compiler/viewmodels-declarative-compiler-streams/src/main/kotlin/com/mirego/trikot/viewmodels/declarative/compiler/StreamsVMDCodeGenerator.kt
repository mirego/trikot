package com.mirego.trikot.viewmodels.declarative.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDPublishedProperty
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import org.reactivestreams.Publisher

class StreamsVMDCodeGenerator(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
) : BaseVMDCodeGenerator(
    codeGenerator = codeGenerator,
    logger = logger
) {
    override val defaultSuperClass: TypeName = VMDViewModelImpl::class.asTypeName()

    override val lifecycleComponentName = "cancellableManager"

    override val lifecycleComponentType = CancellableManager::class

    override val publishedPropertyType = VMDPublishedProperty::class

    override val publisherType = Publisher::class

    override val publisherName = "publisher"

    private val publishedMemberName = MemberName("com.mirego.trikot.viewmodels.declarative.viewmodel.internal", "published")

    override fun CodeBlock.Builder.addPublishedMemberInvocationStatement(propertyName: String): CodeBlock.Builder =
        addStatement("%M(${propertyName}InitialValue, this)", publishedMemberName)

    override fun CodeBlock.Builder.addUpdatePropertyInvocationStatement(propertyName: String, value: String): CodeBlock.Builder =
        addStatement("updatePropertyPublisher(this::${propertyName}, $lifecycleComponentName, $value)")

}

