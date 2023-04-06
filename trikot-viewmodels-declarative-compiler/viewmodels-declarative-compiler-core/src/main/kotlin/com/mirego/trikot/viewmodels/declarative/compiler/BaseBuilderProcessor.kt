package com.mirego.trikot.viewmodels.declarative.compiler

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

abstract class BaseBuilderProcessor(
    private val logger: KSPLogger,
    private val vmdCodeGenerator: BaseVMDCodeGenerator
) : SymbolProcessor {

    private val vmdScanner = VMDScanner(logger)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.logging("Scanning metadata ...")
        val viewModelMetaData = vmdScanner.getViewModelMetaData(resolver, vmdCodeGenerator)
        logger.logging("Metadata: $viewModelMetaData")
        vmdCodeGenerator.generateViewModelParents(viewModelMetaData)
        return emptyList()
    }
}
