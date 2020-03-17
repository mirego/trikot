package com.mirego.trikot.metaviews.lifecycle

import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import org.reactivestreams.Publisher

actual class ApplicationStatePublisher :
    BehaviorSubjectImpl<ApplicationState>(ApplicationState.FOREGROUND), Publisher<ApplicationState>
