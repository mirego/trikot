package com.mirego.trikot.viewmodels.declarative

import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
annotation class Published

@Target(AnnotationTarget.CLASS)
annotation class PublishedSubClass(val superClass: KClass<*>)
