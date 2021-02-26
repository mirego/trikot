package com.mirego.trikot.http.android

import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import com.mirego.trikot.http.connectivity.ConnectivityState
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class AndroidConnectivityPublisher(application: ContextWrapper) :
    BehaviorSubjectImpl<ConnectivityState>() {

    private val connectivityManager: ConnectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val activeNetworkState: ConnectivityState
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?.asConnectivityState()
            } else {
                @Suppress("DEPRECATION")
                connectivityManager.activeNetworkInfo?.asConnectivityState()
            } ?: ConnectivityState.NONE
        }

    private val networkRequest =
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            value = connectivityManager.getNetworkCapabilities(network)?.asConnectivityState()
                ?: ConnectivityState.NONE
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            value = networkCapabilities.asConnectivityState()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            val isConnected = connectivityManager.activeNetworkInfo?.isConnected == true
            if (!isConnected) value = ConnectivityState.NONE
        }
    }

    init {
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
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
private fun NetworkCapabilities.asConnectivityState() = when {
    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        || hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> ConnectivityState.WIFI
    else -> ConnectivityState.CELLULAR
}

@Suppress("DEPRECATION")
private fun NetworkInfo.asConnectivityState() = when (type) {
    ConnectivityManager.TYPE_MOBILE -> ConnectivityState.CELLULAR
    ConnectivityManager.TYPE_WIFI -> ConnectivityState.WIFI
    ConnectivityManager.TYPE_ETHERNET -> ConnectivityState.WIFI
    else -> ConnectivityState.NONE
}
