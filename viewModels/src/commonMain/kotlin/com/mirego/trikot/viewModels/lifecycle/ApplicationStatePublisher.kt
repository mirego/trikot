package com.mirego.trikot.viewmodels.lifecycle

import org.reactivestreams.Publisher

expect class ApplicationStatePublisher() : Publisher<ApplicationState>
