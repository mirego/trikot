package com.mirego.trikot.viewmodels.lifecycle

import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import org.reactivestreams.Publisher

actual class ApplicationStatePublisher :
    BehaviorSubjectImpl<ApplicationState>(ApplicationState.FOREGROUND), Publisher<ApplicationState>
