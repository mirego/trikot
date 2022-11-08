package com.mirego.trikot.viewmodels.declarative.compiler

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.functions
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

fun KParameter.assertParam(expectedName: String, expectedType: KClass<*>) {
    assertEquals(expectedName, name)
    assertEquals(expectedType.createType(), type)
}

fun KParameter.assertParam(expectedName: String, expectedType: KClass<*>, expectedTypeArgument: KClass<*>) {
    assertEquals(expectedName, name)
    assertEquals(expectedType.createType(listOf(KTypeProjection.invariant(expectedTypeArgument.createType()))), type)
}

fun KClass<*>.assertMember(name: String, type: KClass<*>) {
    assertEquals(
        1,
        members.filter {
            it.name == (name) &&
                it.returnType == type.createType()
        }.size
    )
}

fun KClass<*>.assertMember(name: String, type: KClass<*>, typeArgument: KClass<*>) {
    assertEquals(
        1,
        members.filter {
            it.name == (name) &&
                it.returnType == type.createType(listOf(KTypeProjection.invariant(typeArgument.createType())))
        }.size
    )
}

fun KClass<*>.getMandatoryFunction(name: String): KFunction<*> =
    assertNotNull(
        functions.firstOrNull {
            it.name == name
        }
    )

fun <R> KFunction<R>.assertParam(expectedName: String, expectedType: KClass<*>, expectedTypeParameter: KClass<*>) {
    val parameter = parameters.firstOrNull {
        it.name == expectedName
    } ?: fail("Parameter not found $expectedName")
    parameter.assertParam(expectedName, expectedType, expectedTypeParameter)
}

fun <R> KFunction<R>.assertReturnType(type: KClass<*>) {
    assertEquals(type.createType(), returnType)
}

fun <R> KFunction<R>.assertReturnType(type: KClass<*>, typeParameter: KClass<*>) {
    assertEquals(type.createType(listOf(KTypeProjection.invariant(typeParameter.createType()))), returnType)
}
