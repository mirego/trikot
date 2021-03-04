package com.mirego.trikot.viewmodels.declarative.controller.factory

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mirego.trikot.viewmodels.declarative.controller.ViewModelController

class AndroidViewModelProviderFactory {

    companion object {
        @JvmStatic
        fun with(activity: FragmentActivity, constructorParam: Any?) = constructorParam?.let {
            with(activity, listOf(it))
        } ?: with(activity)

        @JvmStatic
        fun with(fragment: Fragment, constructorParam: Any?) = constructorParam?.let {
            with(fragment, listOf(it))
        } ?: with(fragment)

        @JvmStatic
        fun <T : ViewModelController<*, *>> get(
            activity: FragmentActivity,
            constructorParam: Any?,
            clazz: Class<T>
        ) = with(activity, constructorParam).get(clazz)

        @JvmStatic
        fun <T : ViewModelController<*, *>> get(
            fragment: Fragment,
            constructorParam: Any?,
            clazz: Class<T>
        ) = with(fragment, constructorParam).get(clazz)

        private fun with(activity: FragmentActivity, constructorParams: List<Any> = listOf()) =
            ViewModelProvider(
                activity.viewModelStore,
                ParametrizedFactory(activity.application, constructorParams)
            )

        private fun with(fragment: Fragment, constructorParams: List<Any> = listOf()) =
            ViewModelProvider(
                fragment.viewModelStore,
                ParametrizedFactory(
                    fragment.activity?.application
                        ?: throw IllegalStateException("Fragment should be attached to activity"),
                    constructorParams
                )
            )
    }

    class ParametrizedFactory(
        application: Application,
        private val constructorParams: List<Any>
    ) : ViewModelProvider.Factory {
        private val internalFactory =
            (application as ViewModelControllerFactoryProvidingApplication).viewModelControllerFactory

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val getters = internalFactory::class.java.declaredMethods
                .filter {
                    it.returnType == modelClass && it.parameterTypes.map { javaClass ->
                        javaClass.kotlin
                    } == constructorParams.map { constructorParam ->
                        constructorParam::class
                    }
                }

            if (getters.isEmpty()) {
                throw IllegalArgumentException(
                    "Unable to find a method for class: " +
                        "${internalFactory::class.java.simpleName} " +
                        "with return type: ${modelClass.simpleName} " +
                        "and ${getParametersErrorString()}"
                )
            } else if (getters.size > 1) {
                throw IllegalArgumentException(
                    "Found more than one method for class: " +
                        "${internalFactory::class.java.simpleName} " +
                        "with return type: ${modelClass.simpleName} " +
                        "and ${getParametersErrorString()}"
                )
            }

            return getters[0].invoke(internalFactory, *constructorParams.toTypedArray()) as T
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
