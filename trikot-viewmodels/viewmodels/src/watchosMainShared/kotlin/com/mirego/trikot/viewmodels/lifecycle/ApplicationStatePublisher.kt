package com.mirego.trikot.viewmodels.lifecycle

import com.mirego.trikot.foundation.concurrent.atomicNullable
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import org.reactivestreams.Publisher
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSThread
import platform.WatchKit.WKApplicationDidEnterBackgroundNotification
import platform.WatchKit.WKApplicationState
import platform.WatchKit.WKApplicationWillEnterForegroundNotification
import platform.WatchKit.WKExtension
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.sel_registerName

@Suppress("unused")
actual class ApplicationStatePublisher :
    BehaviorSubjectImpl<ApplicationState>(),
    Publisher<ApplicationState> {

    private val observer = ApplicationStateObserver()

    init {
        if (NSThread.isMainThread) {
            setInitialValue()
        } else {
            dispatch_async(
                dispatch_get_main_queue()
            ) {
                setInitialValue()
            }
        }
    }

    private fun setInitialValue() {
        value = when (WKExtension.sharedExtension().applicationState) {
            WKApplicationState.WKApplicationStateBackground -> ApplicationState.BACKGROUND
            else -> ApplicationState.FOREGROUND
        }
    }

    override fun onFirstSubscription() {
        super.onFirstSubscription()
        observer.start {
            value = it
        }
    }

    override fun onNoSubscription() {
        observer.stop()
        super.onNoSubscription()
    }

    private class ApplicationStateObserver : NSObject() {
        private var callback: ((ApplicationState) -> Unit)? by atomicNullable(null)

        @OptIn(ExperimentalForeignApi::class)
        fun start(closure: (ApplicationState) -> Unit) {
            callback = closure
            NSNotificationCenter.defaultCenter.addObserver(
                this,
                sel_registerName("willEnterForeground"),
                WKApplicationWillEnterForegroundNotification,
                null
            )
            NSNotificationCenter.defaultCenter.addObserver(
                this,
                sel_registerName("didEnterBackground"),
                WKApplicationDidEnterBackgroundNotification,
                null
            )
        }

        fun stop() {
            callback = null
            NSNotificationCenter.defaultCenter.removeObserver(this)
        }

        @ObjCAction
        @Suppress("unused")
        fun willEnterForeground() {
            callback?.let { it(ApplicationState.FOREGROUND) }
        }

        @ObjCAction
        @Suppress("unused")
        fun didEnterBackground() {
            callback?.let { it(ApplicationState.BACKGROUND) }
        }
    }
}
