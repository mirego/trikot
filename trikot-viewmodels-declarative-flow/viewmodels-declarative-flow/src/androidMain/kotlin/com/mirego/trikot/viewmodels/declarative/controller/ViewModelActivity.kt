package com.mirego.trikot.viewmodels.declarative.controller

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mirego.trikot.viewmodels.declarative.controller.factory.AndroidViewModelProviderFactory
import com.mirego.trikot.viewmodels.declarative.lifecycle.LifecycleOwnerWrapper
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDViewModel
import kotlin.reflect.KClass

abstract class ViewModelActivity<VMC : VMDViewModelController<VM, N>, VM : VMDViewModel, N : VMDNavigationDelegate> :
    AppCompatActivity(), VMDNavigationDelegate {

    companion object {
        private const val LOG_TAG = "ViewModelActivity"
    }

    protected abstract val viewModelController: VMC
    val viewModel: VM by lazy { viewModelController.viewModel }

    protected lateinit var lifecycleOwnerWrapper: LifecycleOwnerWrapper

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navigationDelegate = this as? N
        if (navigationDelegate != null) {
            viewModelController.navigationDelegate = navigationDelegate
        } else {
            Log.e(
                LOG_TAG,
                "ViewModelActivity ${this::class.simpleName} must conform to its navigation delegate"
            )
        }

        if (savedInstanceState == null) {
            viewModelController.onCreate()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModelController.onAppear()
    }

    override fun onPause() {
        super.onPause()
        viewModelController.onDisappear()
    }

    override fun onDestroy() {
        viewModelController.navigationDelegate = null
        super.onDestroy()
    }

    protected fun <T : VMDViewModelController<VM, N>> getViewModelController(requestedClass: KClass<T>): T =
        AndroidViewModelProviderFactory.with(this, null).get(requestedClass.java)

    protected fun <T : VMDViewModelController<VM, N>> getViewModelController(
        requestedClass: KClass<T>,
        navigationData: Any?
    ): T = if (navigationData == null) {
        getViewModelController(requestedClass)
    } else {
        AndroidViewModelProviderFactory.with(this, navigationData).get(requestedClass.java)
    }
}
