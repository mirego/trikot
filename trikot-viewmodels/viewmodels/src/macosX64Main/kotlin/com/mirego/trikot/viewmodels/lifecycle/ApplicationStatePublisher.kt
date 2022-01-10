package com.mirego.trikot.viewmodels.lifecycle

import com.mirego.trikot.foundation.concurrent.atomicNullable
import com.mirego.trikot.streams.reactive.BehaviorSubjectImpl
import kotlinx.cinterop.ObjCAction
import org.reactivestreams.Publisher
import platform.AppKit.NSApplication
import platform.AppKit.NSApplicationDidBecomeActiveNotification
import platform.AppKit.NSApplicationDidResignActiveNotification
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSThread
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.sel_registerName
import kotlin.native.concurrent.freeze

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
                dispatch_get_main_queue(),
                {
                    setInitialValue()
                }.freeze()
            )
        }
    }

    private fun setInitialValue() {
        value = when (NSApplication.sharedApplication().active) {
            true -> ApplicationState.FOREGROUND
            false -> ApplicationState.BACKGROUND
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

        fun start(closure: (ApplicationState) -> Unit) {
            callback = closure.freeze()
            NSNotificationCenter.defaultCenter.addObserver(
                this,
                sel_registerName("didBecomeActive"),
                NSApplicationDidBecomeActiveNotification,
                null
            )
            NSNotificationCenter.defaultCenter.addObserver(
                this,
                sel_registerName("didResignActive"),
                NSApplicationDidResignActiveNotification,
                null
            )
        }

        fun stop() {
            callback = null
            NSNotificationCenter.defaultCenter.removeObserver(this)
        }

        @ObjCAction
        @Suppress("unused")
        fun didBecomeActive() {
            callback?.let { it(ApplicationState.FOREGROUND) }
        }

        @ObjCAction
        @Suppress("unused")
        fun didResignActive() {
            callback?.let { it(ApplicationState.BACKGROUND) }
        }
    }
}
