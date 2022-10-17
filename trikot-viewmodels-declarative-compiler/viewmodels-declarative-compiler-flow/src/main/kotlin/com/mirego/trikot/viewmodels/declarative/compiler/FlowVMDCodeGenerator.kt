package com.mirego.trikot.viewmodels.declarative.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModelImpl
import com.mirego.trikot.viewmodels.declarative.viewmodel.internal.VMDFlowProperty
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class FlowVMDCodeGenerator(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
) : BaseVMDCodeGenerator(
    codeGenerator = codeGenerator,
    logger = logger
) {
    override val defaultSuperClass: TypeName = VMDViewModelImpl::class.asTypeName()

    override val lifecycleComponentName = "coroutineScope"

    override val lifecycleComponentType = CoroutineScope::class

    override val publishedPropertyType = VMDFlowProperty::class

    override val publisherType = Flow::class

    override val publisherName = "flow"

    private val publishedMemberName = MemberName("com.mirego.trikot.viewmodels.declarative.viewmodel.internal", "emit")

    override fun CodeBlock.Builder.addPublishedMemberInvocationStatement(propertyName: String): CodeBlock.Builder =
        addStatement("%M(${propertyName}InitialValue, this, $lifecycleComponentName)", publishedMemberName)

    override fun CodeBlock.Builder.addUpdatePropertyInvocationStatement(propertyName: String, value: String): CodeBlock.Builder =
        addStatement("updateProperty(this::${propertyName}, $value)")
}

