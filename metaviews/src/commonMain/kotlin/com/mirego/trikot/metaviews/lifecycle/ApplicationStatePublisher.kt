package com.mirego.trikot.metaviews.lifecycle

import org.reactivestreams.Publisher

expect class ApplicationStatePublisher() : Publisher<ApplicationState>
