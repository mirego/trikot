package com.mirego.trikot.http.android

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import com.mirego.trikot.http.connectivity.ConnectivityState
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl

@SuppressLint("MissingPermission")
class AndroidConnectivityPublisher @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE) constructor(
    application: ContextWrapper
) :
    BehaviorSubjectImpl<ConnectivityState>() {

    private val connectivityManager: ConnectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val activeNetworkState: ConnectivityState
        get() = try {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.asConnectivityState() ?: ConnectivityState.NONE
        } catch (e: Throwable) {
            ConnectivityState.NONE
        }

    private val networkRequest =
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            updateActiveNetworkState()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            updateActiveNetworkState()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            updateActiveNetworkState()
        }
    }

    init {
        value = activeNetworkState
    }

    private fun updateActiveNetworkState() {
        value = activeNetworkState
    }

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        value = activeNetworkState
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onNoSubscription() {
        super.onNoSubscription()
        connectivityManager.unregisterNetworkCallback(networkCallback)
        cleanupValues()
    }
}

private fun NetworkCapabilities.asConnectivityState() = when {
    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
        hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> ConnectivityState.WIFI
    else -> ConnectivityState.CELLULAR
}
