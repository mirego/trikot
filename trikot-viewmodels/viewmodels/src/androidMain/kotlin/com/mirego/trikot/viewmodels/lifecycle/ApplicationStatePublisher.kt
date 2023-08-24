package com.mirego.trikot.viewmodels.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.mirego.trikot.foundation.concurrent.dispatchQueue.UIThreadDispatchQueue
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import org.reactivestreams.Publisher

actual class ApplicationStatePublisher :
    BehaviorSubjectImpl<ApplicationState>(),
    Publisher<ApplicationState>,
    DefaultLifecycleObserver {

    private val lifecycle = ProcessLifecycleOwner.get().lifecycle

    init {
        value =
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) ApplicationState.FOREGROUND else ApplicationState.BACKGROUND
    }

    override fun onFirstSubscription() {
        super.onFirstSubscription()

        UIThreadDispatchQueue().dispatch {
            lifecycle.addObserver(this)
        }
    }

    override fun onNoSubscription() {
        UIThreadDispatchQueue().dispatch {
            lifecycle.removeObserver(this)
        }

        super.onNoSubscription()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        value = ApplicationState.FOREGROUND
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        value = ApplicationState.BACKGROUND
    }
}
