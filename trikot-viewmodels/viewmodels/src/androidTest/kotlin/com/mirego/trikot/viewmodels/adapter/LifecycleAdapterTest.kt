package com.mirego.trikot.viewmodels.adapter

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mirego.trikot.viewmodels.TestActivity
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.R])
class LifecycleAdapterTest {

    @Test
    fun testLifecycle_simpleScenario() {
        ActivityScenario.launch(TestActivity::class.java).apply {
            moveToState(Lifecycle.State.CREATED)
            onActivity { activity ->
                val adapter = Adapter(activity)
                val holder = adapter.onCreateViewHolder(FrameLayout(activity), 0)
                assertEquals(Lifecycle.State.INITIALIZED, holder.lifecycle.currentState)
                adapter.onBindViewHolder(holder, 0)
                assertEquals(Lifecycle.State.RESUMED, holder.lifecycle.currentState)
                moveToState(Lifecycle.State.DESTROYED)
                assertEquals(Lifecycle.State.INITIALIZED, holder.lifecycle.currentState)
                assertEquals(1, holder.onAttachCalls)
                assertEquals(1, holder.onDetachCalls)
            }
        }
    }

    @Test
    fun testLifecycle_detachAttachScenario() {
        ActivityScenario.launch(TestActivity::class.java).apply {
            moveToState(Lifecycle.State.CREATED)
            onActivity { activity ->
                val adapter = Adapter(activity)
                val holder = adapter.onCreateViewHolder(FrameLayout(activity), 0)
                assertEquals(Lifecycle.State.INITIALIZED, holder.lifecycle.currentState)
                adapter.onBindViewHolder(holder, 0)
                assertEquals(Lifecycle.State.RESUMED, holder.lifecycle.currentState)
                adapter.onBindViewHolder(holder, 0)
                assertEquals(Lifecycle.State.RESUMED, holder.lifecycle.currentState)
                adapter.onViewDetachedFromWindow(holder)
                assertEquals(Lifecycle.State.CREATED, holder.lifecycle.currentState)
                adapter.onViewAttachedToWindow(holder)
                assertEquals(Lifecycle.State.RESUMED, holder.lifecycle.currentState)
                moveToState(Lifecycle.State.DESTROYED)
                assertEquals(Lifecycle.State.INITIALIZED, holder.lifecycle.currentState)
                assertEquals(2, holder.onAttachCalls)
                assertEquals(2, holder.onDetachCalls)
            }
        }
    }

    @Test
    fun testLifecycle_recyclingScenario() {
        ActivityScenario.launch(TestActivity::class.java).apply {
            moveToState(Lifecycle.State.CREATED)
            onActivity { activity ->
                val adapter = Adapter(activity)
                val holder = adapter.onCreateViewHolder(FrameLayout(activity), 0)
                adapter.onBindViewHolder(holder, 0)
                adapter.onViewDetachedFromWindow(holder)
                adapter.onViewRecycled(holder)
                assertEquals(Lifecycle.State.INITIALIZED, holder.lifecycle.currentState)
                assertEquals(1, holder.onAttachCalls)
                assertEquals(1, holder.onDetachCalls)
            }
        }
    }

    @Test
    fun testLifecycle_notBoundScenario() {
        ActivityScenario.launch(TestActivity::class.java).apply {
            moveToState(Lifecycle.State.CREATED)
            onActivity { activity ->
                val adapter = Adapter(activity)
                val holder = adapter.onCreateViewHolder(FrameLayout(activity), 0)
                assertEquals(Lifecycle.State.INITIALIZED, holder.lifecycle.currentState)
                moveToState(Lifecycle.State.DESTROYED)
                assertEquals(Lifecycle.State.INITIALIZED, holder.lifecycle.currentState)
                assertEquals(0, holder.onAttachCalls)
                assertEquals(0, holder.onDetachCalls)
            }
        }
    }

    @Test
    fun testLifecycle_multipleBindScenario() {
        ActivityScenario.launch(TestActivity::class.java).apply {
            moveToState(Lifecycle.State.CREATED)
            onActivity { activity ->
                val adapter = Adapter(activity)
                val holder = adapter.onCreateViewHolder(FrameLayout(activity), 0)
                assertEquals(Lifecycle.State.INITIALIZED, holder.lifecycle.currentState)
                adapter.onBindViewHolder(holder, 0)
                adapter.onBindViewHolder(holder, 0)
                adapter.onBindViewHolder(holder, 0)
                adapter.onViewRecycled(holder)
                adapter.onBindViewHolder(holder, 0)
                moveToState(Lifecycle.State.DESTROYED)
                assertEquals(Lifecycle.State.INITIALIZED, holder.lifecycle.currentState)
                assertEquals(2, holder.onAttachCalls)
                assertEquals(2, holder.onDetachCalls)
            }
        }
    }

    private class Adapter(lifecycleOwner: LifecycleOwner) :
        LifecycleAdapter<String, LifecycleAdapter.LifecycleViewHolder>(lifecycleOwner, NoDiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(View(parent.context))
    }

    private class ViewHolder(itemView: View) : LifecycleAdapter.LifecycleViewHolder(itemView) {
        var onAttachCalls = 0
        var onDetachCalls = 0
        override fun onAttach() {
            super.onAttach()
            onAttachCalls ++
        }

        override fun onDetach() {
            super.onDetach()
            onDetachCalls++
        }
    }
}
