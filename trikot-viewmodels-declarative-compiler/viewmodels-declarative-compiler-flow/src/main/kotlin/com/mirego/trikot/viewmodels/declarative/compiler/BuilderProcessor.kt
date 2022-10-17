package com.mirego.trikot.viewmodels.declarative.compiler

import com.google.devtools.ksp.processing.*

class BuilderProcessor(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
) : BaseBuilderProcessor(
    logger = logger,
    vmdCodeGenerator = FlowVMDCodeGenerator(
        codeGenerator = codeGenerator,
        logger = logger
    )
)

class BuilderProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment,
    ): SymbolProcessor {
        return BuilderProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger
        )
    }
}
