package com.mirego.trikot.http.web

import com.mirego.trikot.http.connectivity.ConnectivityState
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import kotlinx.browser.window
import org.w3c.dom.events.Event

class WebConnectivityPublisher : BehaviorSubjectImpl<ConnectivityState>() {
    private val activeNetworkState: ConnectivityState
        get() {
            if (window.navigator.onLine) return ConnectivityState.WIFI
            return ConnectivityState.NONE
        }

    @Suppress("UNUSED_PARAMETER")
    private fun onChange(event: Event) {
        value = activeNetworkState
    }

    init {
        value = activeNetworkState
    }

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        value = activeNetworkState
        window.addEventListener("offline", ::onChange)
        window.addEventListener("online", ::onChange)
    }

    override fun onNoSubscription() {
        super.onNoSubscription()
        window.removeEventListener("offline", ::onChange)
        window.removeEventListener("online", ::onChange)
    }
}
