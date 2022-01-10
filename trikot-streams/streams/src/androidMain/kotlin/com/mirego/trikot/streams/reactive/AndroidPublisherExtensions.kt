package com.mirego.trikot.streams.reactive

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.reactivestreams.Publisher

typealias ObserveBlock<T> = (T) -> Unit

fun <T> Publisher<T>.observe(lifecycleOwner: LifecycleOwner, observeBlock: ObserveBlock<T>) =
    asLiveData().observe(lifecycleOwner = lifecycleOwner, observeBlock = observeBlock)

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, observeBlock: ObserveBlock<T>) =
    observe(lifecycleOwner, Observer { observeBlock(it) })

fun <T> Publisher<T>.asLiveData(): LiveData<T> = LiveDataReactiveStreams.fromPublisher(this)
