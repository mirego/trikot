package com.mirego.trikot.viewmodels.lifecycle

import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import kotlinx.browser.document
import org.reactivestreams.Publisher
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

actual class ApplicationStatePublisher :
    BehaviorSubjectImpl<ApplicationState>(ApplicationState.FOREGROUND),
    Publisher<ApplicationState> {

    private val visibilityChangeEventListener =
        VisibilityChangeEventListener(this)

    companion object {
        private const val VISIBILITY_CHANGE_EVENT = "visibilitychange"
        private const val VISIBILITY_STATE_HIDDEN = "hidden"
    }

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        document.addEventListener(VISIBILITY_CHANGE_EVENT, visibilityChangeEventListener)
    }

    override fun onNoSubscription() {
        document.removeEventListener(VISIBILITY_CHANGE_EVENT, visibilityChangeEventListener)
        super.onNoSubscription()
    }

    private class VisibilityChangeEventListener(
        val applicationStatePublisher: ApplicationStatePublisher
    ) : EventListener {
        override fun handleEvent(event: Event) {
            val document: dynamic = document
            applicationStatePublisher.value =
                if (document.visibilityState == VISIBILITY_STATE_HIDDEN) {
                    ApplicationState.BACKGROUND
                } else {
                    ApplicationState.FOREGROUND
                }
        }
    }
}
