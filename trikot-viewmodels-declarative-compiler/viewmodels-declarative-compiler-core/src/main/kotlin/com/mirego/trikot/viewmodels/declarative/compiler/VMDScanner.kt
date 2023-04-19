package com.mirego.trikot.viewmodels.declarative.compiler

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getKotlinClassByName
import com.google.devtools.ksp.isAbstract
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.mirego.trikot.viewmodels.declarative.Published
import com.mirego.trikot.viewmodels.declarative.PublishedSubClass

@OptIn(KspExperimental::class)
class VMDScanner(
    private val logger: KSPLogger
) {
    fun getViewModelMetaData(
        resolver: Resolver,
        vmdCodeGenerator: BaseVMDCodeGenerator
    ): List<ViewModelMetaData> {
        val subClasses = resolver.getSymbolsWithAnnotation(PublishedSubClass::class.qualifiedName!!)
        return resolver.getSymbolsWithAnnotation(Published::class.qualifiedName!!)
            .groupBy { it.parent }
            .map { entry ->
                logger.logging("Annotated entity $entry")
                val viewModelInterface = entry.key as KSClassDeclaration
                val superClassDeclaration: KSClassDeclaration? = getSuperClassDeclaration(
                    subClasses = subClasses,
                    viewModelInterface = viewModelInterface,
                    resolver = resolver
                )

                val viewModelAbstractProperties = getViewModelAbstractProperties(vmdCodeGenerator, resolver, viewModelInterface)
                val viewModelAbstractMethods = getViewModelAbstractMethods(vmdCodeGenerator, resolver, viewModelInterface)

                ViewModelMetaData(
                    viewModelInterface = viewModelInterface,
                    publishedProperty = entry.value.map { it as KSPropertyDeclaration },
                    superClass = superClassDeclaration,
                    allFieldsArePublished = entry.value.size == viewModelAbstractProperties.count(),
                    hasAbstractMethods = viewModelAbstractMethods.isNotEmpty()
                )
            }
    }

    private fun getSuperClassDeclaration(
        subClasses: Sequence<KSAnnotated>,
        viewModelInterface: KSClassDeclaration,
        resolver: Resolver
    ): KSClassDeclaration? {
        val subClass: KSAnnotated? = getViewModelSubclass(subClasses, viewModelInterface)
        val annotation: KSAnnotation? = getAnnotation(subClass)
        return getSuperClassParam(annotation, resolver)
    }

    private fun getViewModelSubclass(
        subClasses: Sequence<KSAnnotated>,
        viewModelInterface: KSClassDeclaration
    ): KSAnnotated? =
        subClasses.firstOrNull { subClass ->
            (subClass as KSClassDeclaration).getAllSuperTypes()
                .any { subClassSuperType ->
                    subClassSuperType == viewModelInterface.asStarProjectedType()
                }
        }

    private fun getAnnotation(subClass: KSAnnotated?): KSAnnotation? {
        val annotation: KSAnnotation? = subClass?.annotations?.filter {
            it.shortName.getShortName() == PublishedSubClass::class.simpleName && it.annotationType.resolve().declaration
                .qualifiedName?.asString() == PublishedSubClass::class.qualifiedName
        }?.firstOrNull()
        return annotation
    }

    private fun getSuperClassParam(
        annotation: KSAnnotation?,
        resolver: Resolver
    ): KSClassDeclaration? =
        annotation?.arguments?.firstOrNull()?.let { argument ->
            val value = argument.value as KSType
            value.declaration.qualifiedName?.asString()?.let {
                resolver.getKotlinClassByName(it)
            }
        }

    private fun getViewModelAbstractProperties(
        vmdCodeGenerator: BaseVMDCodeGenerator,
        resolver: Resolver,
        viewModelInterface: KSClassDeclaration
    ): List<KSPropertyDeclaration> {
        val baseViewModelProperties = vmdCodeGenerator.viewModelType.qualifiedName?.let {
            resolver.getKotlinClassByName(it)?.getAllProperties()
        }.orEmpty()
        return viewModelInterface.getAllProperties()
            .filter { vmProperty ->
                vmProperty.isAbstract()
            }
            .filterNot { vmProperty ->
                baseViewModelProperties.any { baseProperty ->
                    baseProperty.simpleName == vmProperty.simpleName
                }
            }
            .toList()
    }

    private fun getViewModelAbstractMethods(
        vmdCodeGenerator: BaseVMDCodeGenerator,
        resolver: Resolver,
        viewModelInterface: KSClassDeclaration
    ): List<KSFunctionDeclaration> {
        val baseViewModelMethods = vmdCodeGenerator.viewModelType.qualifiedName?.let {
            resolver.getKotlinClassByName(it)?.getAllFunctions()
        }.orEmpty()
        return viewModelInterface.getAllFunctions()
            .filterNot { vmProperty ->
                baseViewModelMethods.any { baseProperty ->
                    baseProperty.simpleName == vmProperty.simpleName
                }
            }
            .toList()
    }
}
