package com.mirego.trikot.viewmodels.declarative.controller

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.mirego.trikot.viewmodels.declarative.ViewModel
import com.mirego.trikot.viewmodels.declarative.controller.factory.AndroidViewModelProviderFactory
import com.mirego.trikot.viewmodels.declarative.lifecycle.LifecycleOwnerWrapper
import kotlin.reflect.KClass

abstract class ViewModelFragment<VMC : ViewModelController<VM, N>, VM : ViewModel, N : NavigationDelegate> :
    Fragment(), NavigationDelegate {

    companion object {
        private const val LOG_TAG = "ViewModelFragment"
    }

    abstract val viewModelController: VMC

    private lateinit var lifecycleOwnerWrapper: LifecycleOwnerWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModelController.onCreate()
        }

        lifecycleOwnerWrapper = LifecycleOwnerWrapper(this)

        @Suppress("UNCHECKED_CAST")
        val navigationDelegate = this as? N
        if (navigationDelegate != null) {
            viewModelController.navigationDelegate = navigationDelegate
        } else {
            Log.e(
                LOG_TAG,
                "Fragment ${this::class.simpleName} must conform to its navigation delegate"
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelController.navigationDelegate = null
    }

    protected fun <T : ViewModelController<VM, N>> getViewModelController(requestedClass: KClass<T>): T =
        AndroidViewModelProviderFactory.with(this, null).get(requestedClass.java)

    protected fun <T : ViewModelController<VM, N>> getViewModelController(
        requestedClass: KClass<T>,
        navigationData: Any?
    ): T = if (navigationData == null) {
        getViewModelController(requestedClass)
    } else {
        AndroidViewModelProviderFactory.with(this, navigationData).get(requestedClass.java)
    }
}
