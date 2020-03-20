package com.trikot.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mirego.trikot.viewmodels.LifecycleOwnerWrapper
import com.trikot.sample.viewmodels.AndroidAppViewModel
import com.trikot.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var appViewModel: AndroidAppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProviders.of(this).get(AndroidAppViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = appViewModel.vm
        binding.lifecycleOwner = this
        binding.lifecycleOwnerWrapper = LifecycleOwnerWrapper(this)
    }
}
