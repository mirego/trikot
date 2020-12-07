package com.trikot.sample.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.mirego.trikot.viewmodels.LifecycleOwnerWrapper
import com.trikot.sample.BR
import com.trikot.sample.viewmodels.base.BaseNavigationDelegate
import com.trikot.sample.viewmodels.base.BaseViewModelController
import com.trikot.sample.viewmodels.base.ViewModelController
import kotlin.reflect.KClass

abstract class BaseViewModelActivity<NavigationDelegate : BaseNavigationDelegate,
        VM : com.mirego.trikot.viewmodels.ViewModel> :
    AppCompatActivity(),
    BaseNavigationDelegate {

    private lateinit var lifecycleOwnerWrapper: LifecycleOwnerWrapper

    protected abstract val viewModelController: BaseViewModelController<NavigationDelegate, VM>
    protected abstract val binding: ViewDataBinding

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.setVariable(BR.viewModel, viewModelController.viewModel)
        binding.lifecycleOwner = this
        lifecycleOwnerWrapper = LifecycleOwnerWrapper(this)
        binding.setVariable(BR.lifecycleOwnerWrapper, lifecycleOwnerWrapper)
        (this as? NavigationDelegate)?.let {
            viewModelController.navigationDelegate = it
        }
    }

    override fun onDestroy() {
        viewModelController.navigationDelegate = null
        binding.unbind()
        super.onDestroy()
    }

    protected fun <T : ViewModelController> getViewModelController(requestedClass: KClass<T>): T =
        AndroidViewModelProviderFactory.with(this, null)
            .get(requestedClass.java)

    private fun <T : ViewModelController> getViewModelController(
        requestedClass: KClass<T>,
        serializedData: String
    ): T =
        AndroidViewModelProviderFactory.with(this, serializedData)
            .get(requestedClass.java)
}
