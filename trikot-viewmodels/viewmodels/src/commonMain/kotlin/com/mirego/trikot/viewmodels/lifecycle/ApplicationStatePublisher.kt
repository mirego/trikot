package com.mirego.trikot.viewmodels.lifecycle

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

expect class ApplicationStatePublisher() : Publisher<ApplicationState> {
    override fun subscribe(s: Subscriber<in ApplicationState>)
}
