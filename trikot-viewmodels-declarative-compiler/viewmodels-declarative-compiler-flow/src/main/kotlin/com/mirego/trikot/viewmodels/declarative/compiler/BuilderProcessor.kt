package com.mirego.trikot.viewmodels.declarative.compiler

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class BuilderProcessor(
    codeGenerator: CodeGenerator,
    logger: KSPLogger
) : BaseBuilderProcessor(
    logger = logger,
    vmdCodeGenerator = FlowVMDCodeGenerator(
        codeGenerator = codeGenerator,
        logger = logger
    )
)

class BuilderProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return BuilderProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger
        )
    }
}
