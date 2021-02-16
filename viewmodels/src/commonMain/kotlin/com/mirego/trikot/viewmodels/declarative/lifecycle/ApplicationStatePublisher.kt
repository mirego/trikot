package com.mirego.trikot.viewmodels.declarative.lifecycle

import org.reactivestreams.Publisher

expect class ApplicationStatePublisher() : Publisher<ApplicationState>
