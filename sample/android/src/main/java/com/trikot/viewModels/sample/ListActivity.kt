package com.trikot.viewmodels.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mirego.trikot.viewmodels.LifecycleOwnerWrapper
import com.trikot.viewmodels.sample.databinding.ActivityListBinding
import com.trikot.viewmodels.sample.databinding.ListItemViewModelAdapter
import com.trikot.viewmodels.sample.navigation.Destination
import com.trikot.viewmodels.sample.navigation.NavigationDelegate
import androidx.appcompat.app.AlertDialog

class ListActivity: AppCompatActivity(), NavigationDelegate {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityListBinding>(this, R.layout.activity_list)
        binding.datasets.adapter = ListItemViewModelAdapter()
        binding.viewModel = Destination.values()[intent.getIntExtra(EXTRA_DESTINATION, 0)].getViewModel(this)
        binding.lifecycleOwner = this
        binding.lifecycleOwnerWrapper = LifecycleOwnerWrapper(this)
    }
}

fun Context.startListActivity(destination: Destination) {
    startActivity(with(Intent(this, ListActivity::class.java)) {
        putExtra(EXTRA_DESTINATION, destination.ordinal)
    })
}

const val EXTRA_DESTINATION = "EXTRA_DESTINATION"
