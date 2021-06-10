package com.trikot.sample.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trikot.sample.factories.Bootstrap
import com.trikot.sample.viewmodels.base.BaseViewModelController

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
        fun <T : BaseViewModelController<*, *>> get(
            activity: FragmentActivity,
            constructorParam: Any?,
            clazz: Class<T>
        ) = with(activity, constructorParam).get(clazz)

        @JvmStatic
        fun <T : BaseViewModelController<*, *>> get(
            fragment: Fragment,
            constructorParam: Any?,
            clazz: Class<T>
        ) = with(fragment, constructorParam).get(clazz)

        private fun with(activity: FragmentActivity, constructorParams: List<Any> = listOf()) =
            ViewModelProvider(
                activity.viewModelStore,
                ParametrizedFactory(constructorParams)
            )

        private fun with(fragment: Fragment, constructorParams: List<Any> = listOf()) =
            ViewModelProvider(
                fragment.viewModelStore,
                ParametrizedFactory(constructorParams)
            )
    }

    class ParametrizedFactory(
        private val constructorParams: List<Any>
    ) : ViewModelProvider.Factory {
        private val internalFactory = Bootstrap.shared.viewModelControllerFactory

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
                        "${internalFactory::class.java.simpleName} with return type: " +
                        "${modelClass.simpleName} and ${getParametersErrorString()}"
                )
            } else if (getters.size > 1) {
                throw IllegalArgumentException(
                    "Found more than one method for class: " +
                        "${internalFactory::class.java.simpleName} with return type: " +
                        "${modelClass.simpleName} and ${getParametersErrorString()}"
                )
            }

            return getters[0].invoke(internalFactory, *constructorParams.toTypedArray()) as T
        }

        private fun getParametersErrorString(): String {
            if (constructorParams.isEmpty()) {
                return "no parameters"
            }

            return "parameters: ${constructorParams.joinToString(
                separator = ", "
            ) { it::class.java.simpleName }}"
        }
    }
}
