package com.mirego.trikot.viewmodels.lifecycle

import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import org.reactivestreams.Publisher
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSThread
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationDidEnterBackgroundNotification
import platform.UIKit.UIApplicationState
import platform.UIKit.UIApplicationWillEnterForegroundNotification
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.sel_registerName
import kotlin.native.concurrent.freeze

actual class ApplicationStatePublisher :
    BehaviorSubjectImpl<ApplicationState>(),
    Publisher<ApplicationState> {
    init {
        if (NSThread.isMainThread) {
            setInitialValue()
        } else {
            dispatch_async(
                dispatch_get_main_queue(),
                {
                    setInitialValue()
                }.freeze()
            )
        }
    }

    private fun setInitialValue() {
        value = when (UIApplication.sharedApplication.applicationState) {
            UIApplicationState.UIApplicationStateBackground -> ApplicationState.BACKGROUND
            else -> ApplicationState.FOREGROUND
        }
    }

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        NSNotificationCenter.defaultCenter.addObserver(
            this,
            sel_registerName("willEnterForeground"),
            UIApplicationWillEnterForegroundNotification,
            null
        )
        NSNotificationCenter.defaultCenter.addObserver(
            this,
            sel_registerName("didEnterBackground"),
            UIApplicationDidEnterBackgroundNotification,
            null
        )
    }

    override fun onNoSubscription() {
        NSNotificationCenter.defaultCenter.removeObserver(this)
        super.onNoSubscription()
    }

    @Suppress("unused")
    fun willEnterForeground() {
        value = ApplicationState.FOREGROUND
    }

    @Suppress("unused")
    fun didEnterBackground() {
        value = ApplicationState.BACKGROUND
    }
}
