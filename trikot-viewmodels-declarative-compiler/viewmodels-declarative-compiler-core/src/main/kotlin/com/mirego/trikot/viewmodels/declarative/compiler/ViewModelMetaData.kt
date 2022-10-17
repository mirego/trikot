package com.mirego.trikot.viewmodels.declarative.compiler

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

data class ViewModelMetaData(
    val viewModelInterface: KSClassDeclaration,
    val publishedProperty: List<KSPropertyDeclaration>,
    val superClass: KSClassDeclaration?
)
