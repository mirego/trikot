package com.mirego.trikot.viewmodels

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

/**
 * Android extensions around a ViewModelProvider which uses reflection to instanciate
 * view models and optionally pass it a string argument. The argument is passed to the activity
 * intent and the fragment arguments.
 *
 * Terminology: viewModelController is simply the root view model for the activity / fragment .
 *              but it could be any view model.
 * Usage:
 * <pre>
 * [fragment|activity].getViewModelController(viewModelControllerFactory, ViewModel::class)
 * </pre>
 *
 * Note: When using proguard, add exclusion rules for your ViewModelControllerFactory
 * <pre>
 * -keepclassmembers class * implements com.acme.app.ViewModelControllerFactory { <methods>; }
 * </pre>
 */

private const val KEY_SERIALIZED_DATA = "AndroidViewModelFactory.SerializedData"

fun <T : ViewModel> Fragment.getViewModelController(
    viewModelControllerFactory: Any,
    requestedClass: KClass<T>
): T {
    return AndroidViewModelProviderFactory.with(
        viewModelControllerFactory,
        this,
        extraSerializedData
    )
        .get(requestedClass.java)
}

fun <T : ViewModel> Fragment.getViewModelController(
    viewModelControllerFactory: Any,
    requestedClass: KClass<T>,
    serializedData: String
): T {
    return AndroidViewModelProviderFactory.with(viewModelControllerFactory, this, serializedData)
        .get(serializedData, requestedClass.java)
}

fun <T : ViewModel> ComponentActivity.getViewModelController(
    viewModelControllerFactory: Any,
    requestedClass: KClass<T>
): T =
    AndroidViewModelProviderFactory.with(viewModelControllerFactory, this, extraSerializedData)
        .get(requestedClass.java)

fun <T : ViewModel> ComponentActivity.getViewModelController(
    viewModelControllerFactory: Any,
    requestedClass: KClass<T>,
    serializedData: String
): T =
    AndroidViewModelProviderFactory.with(viewModelControllerFactory, this, serializedData)
        .get(serializedData, requestedClass.java)

val ComponentActivity.extraSerializedData: String?
    get() = intent.getStringExtra(KEY_SERIALIZED_DATA)

fun Intent.extraSerializedData(serializedData: String) = apply {
    putExtra(KEY_SERIALIZED_DATA, serializedData)
}

fun Bundle.extraSerializedData(serializedData: String) = apply {
    putString(KEY_SERIALIZED_DATA, serializedData)
}

val Fragment.extraSerializedData: String?
    get() = arguments?.getString(KEY_SERIALIZED_DATA)

class AndroidViewModelProviderFactory {

    companion object {
        @JvmStatic
        fun with(
            viewModelControllerFactory: Any,
            activity: ComponentActivity,
            constructorParam: Any?
        ) = constructorParam?.let {
            with(viewModelControllerFactory, activity, listOf(it))
        } ?: with(viewModelControllerFactory, activity)

        @JvmStatic
        fun with(viewModelControllerFactory: Any, fragment: Fragment, constructorParam: Any?) =
            constructorParam?.let {
                with(viewModelControllerFactory, fragment, listOf(it))
            } ?: with(viewModelControllerFactory, fragment)

        @JvmStatic
        fun <T : ViewModel> get(
            viewModelControllerFactory: Any,
            activity: ComponentActivity,
            serializedData: String?,
            clazz: Class<T>
        ) =
            with(viewModelControllerFactory, activity, serializedData).getWithNullableKey(
                serializedData,
                clazz
            )

        @JvmStatic
        fun <T : ViewModel> get(
            viewModelControllerFactory: Any,
            fragment: Fragment,
            serializedData: String?,
            clazz: Class<T>
        ) =
            with(viewModelControllerFactory, fragment, serializedData).getWithNullableKey(
                serializedData,
                clazz
            )

        private fun <T : ViewModel> ViewModelProvider.getWithNullableKey(
            serializedData: String?,
            clazz: Class<T>
        ) =
            if (serializedData == null) get(clazz) else get(serializedData, clazz)

        private fun with(
            viewModelControllerFactory: Any,
            activity: ComponentActivity,
            constructorParams: List<Any> = listOf()
        ) =
            ViewModelProvider(
                activity.viewModelStore,
                ParametrizedFactory(viewModelControllerFactory, constructorParams)
            )

        private fun with(
            viewModelControllerFactory: Any,
            fragment: Fragment,
            constructorParams: List<Any> = listOf()
        ) =
            ViewModelProvider(
                fragment.viewModelStore,
                ParametrizedFactory(
                    viewModelControllerFactory,
                    constructorParams
                )
            )
    }

    class ParametrizedFactory(
        private val viewModelControllerFactory: Any,
        private val constructorParams: List<Any>
    ) : ViewModelProvider.Factory {
        private val viewModelControllerFactoryClass = viewModelControllerFactory::class.java

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val getters = viewModelControllerFactoryClass.declaredMethods
                .filter {
                    it.returnType == modelClass && it.parameterTypes.map { javaClass ->
                        javaClass.kotlin
                    } == constructorParams.map { constructorParam ->
                        constructorParam::class
                    }
                }

            if (getters.isEmpty()) {
                throw IllegalArgumentException(
                    "Unable to find a method for class: ${viewModelControllerFactoryClass.simpleName} with return type: ${modelClass.simpleName} and ${getParametersErrorString()}"
                )
            } else if (getters.size > 1) {
                throw IllegalArgumentException(
                    "Found more than one method for class: ${viewModelControllerFactoryClass.simpleName} with return type: ${modelClass.simpleName} and ${getParametersErrorString()}"
                )
            }

            return getters[0].invoke(
                viewModelControllerFactory,
                *constructorParams.toTypedArray()
            ) as T
        }

        private fun getParametersErrorString(): String {
            if (constructorParams.isEmpty()) {
                return "no parameters"
            }

            return "parameters: ${
            constructorParams.joinToString(
                separator = ", "
            ) { it::class.java.simpleName }
            }"
        }
    }
}
