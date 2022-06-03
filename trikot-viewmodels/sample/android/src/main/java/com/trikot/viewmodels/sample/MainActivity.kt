package com.trikot.viewmodels.sample

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.trikot.viewmodels.sample.databinding.ActivityMainBinding
import com.trikot.viewmodels.sample.databinding.ListItemViewModelAdapter
import com.trikot.viewmodels.sample.navigation.Destination
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import com.trikot.viewmodels.sample.viewmodels.AndroidAppViewModel

class MainActivity : AppCompatActivity(), NavigationDelegate {
    lateinit var binding: ActivityMainBinding
    private lateinit var appViewModel: AndroidAppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProvider(this)[AndroidAppViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.datasets.adapter = ListItemViewModelAdapter(this)
        binding.viewModel = appViewModel.getVm(this)
        binding.lifecycleOwner = this
    }

    override fun navigateTo(destination: Destination) {
        startListActivity(destination)
    }

    override fun showAlert(text: String) {
        AlertDialog.Builder(this)
            .setTitle(text)
            .setNegativeButton(android.R.string.yes, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}
