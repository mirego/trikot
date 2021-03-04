package com.mirego.trikot.viewmodels.declarative.controller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.mirego.trikot.viewmodels.declarative.ViewModel
import com.mirego.trikot.viewmodels.declarative.controller.factory.AndroidViewModelProviderFactory
import com.mirego.trikot.viewmodels.declarative.lifecycle.LifecycleOwnerWrapper
import kotlin.reflect.KClass

abstract class ViewModelFragment<VMC : ViewModelController<VM, N>, VM : ViewModel, N : NavigationDelegate> :
    Fragment(), NavigationDelegate {

    companion object {
        private val LOG_TAG = "ViewModelFragment"
    }

    abstract val viewModelController: VMC
    protected abstract val binding: ViewDataBinding

    private lateinit var lifecycleOwnerWrapper: LifecycleOwnerWrapper

    private var created = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!created) {
            viewModelController.onCreate()
            created = true
        }

        binding.lifecycleOwner = this
        lifecycleOwnerWrapper = LifecycleOwnerWrapper(this)
        configureBinding(binding, viewModelController.viewModel, lifecycleOwnerWrapper)

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

        return binding.root
    }

    open fun configureBinding(
        binding: ViewDataBinding,
        viewModel: VM,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        // Override in subclass to set variables on binding
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelController.navigationDelegate = null
        binding.unbind()
    }

    protected fun <T : ViewModelController<N, VM>> getViewModelController(requestedClass: KClass<T>): T =
        AndroidViewModelProviderFactory.with(this, null).get(requestedClass.java)

    protected fun <T : ViewModelController<N, VM>> getViewModelController(
        requestedClass: KClass<T>,
        navigationData: Any?
    ): T = if (navigationData == null) {
        getViewModelController(requestedClass)
    } else {
        AndroidViewModelProviderFactory.with(this, navigationData).get(requestedClass.java)
    }
}
