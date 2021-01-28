package com.mirego.trikot.viewmodels.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import org.reactivestreams.Publisher

actual class ApplicationStatePublisher : BehaviorSubjectImpl<ApplicationState>(),
    Publisher<ApplicationState>, LifecycleObserver {

    private val lifecycle = ProcessLifecycleOwner.get().lifecycle

    init {
        value =
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) ApplicationState.FOREGROUND else ApplicationState.BACKGROUND
    }

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        lifecycle.addObserver(this)
    }

    override fun onNoSubscription() {
        lifecycle.removeObserver(this)
        super.onNoSubscription()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    @Suppress("unused")
    fun onMoveToForeground() {
        value = ApplicationState.FOREGROUND
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    @Suppress("unused")
    fun onMoveToBackground() {
        value = ApplicationState.BACKGROUND
    }
}
