# Trikot.foundation
Kotlin Multiplatform implementation of threads, timers, atomic references and iso8601 dates.

## AtomicReference and AtomicListReference
AtomicReference implementation on all platform 
```kotlin
val atom = AtomicReference("a")
atom.compareAndSet("a", "b")
atom.setOrThrow("c", "b") // Will throw

val list = AtomicListReference<String>()
list.add("a") // Thread safely adds "a"
list.remove("a") // Thread safely removes "a"
```

## Timers
Single and repeatable multiplatform timer implementations

```kotlin
 val doOnceTimer = TimerFactory.single(12.seconds) { doSomething() }
 val repeatTimer = TimerFactory.repeatable(12.seconds) { doSomething() }
 repeatTimer.cancel() // stop the timer
```

## Dates (Basic from and to ISO8601)
Multiplatform iso date implementation

```kotlin
val now = Date.now()            // GMT
val date = now + 5.seconds
val isoDate = date.toISO8601()  // yyyy-MM-dd:mm:dd:ssZ
val otherDate = Date.fromISO8601(isoDate)
otherDate == date               // true
```

## Multiplatform freezing
Allow freezing in common code. Does nothing in js and JVM.

```kotlin
freeze(objectToFreeze)
```

In swift, use access freeze via MrFreezeKt class helper to freeze object.
```swift
MrFreezeKt.freeze(objectToFreeze: objectToFreeze)
```

## Dispatch Queues
While waiting for [Sharing of coroutines across threads in Kotlin/Native](https://github.com/Kotlin/kotlinx.coroutines/pull/1648) to work correctly. Trikot.foundation provide a standard Thread model based on queues. When this issue will be resolved, DispatchQueues will be converted to Coroutines.

See: [Dispatch Queues](./documentation/DISPATCH_QUEUES.md) 

## Installation
##### Import dependencies
```groovy
    api "com.mirego.trikot:trikotFoundation:$trikot_foundation_version"
    jvm "com.mirego.trikot:trikotFoundation-jvm:$trikot_foundation_version"
    js "com.mirego.trikot:trikotFoundation-js:$trikot_foundation_version"
    android "com.mirego.trikot:trikotFoundation-android:$trikot_foundation_version"
    iosx64 "com.mirego.trikot:trikotFoundation-iosx64:$trikot_foundation_version"
    iosarm64 "com.mirego.trikot:trikotFoundation-iosarm64:$trikot_foundation_version"
    iosarm32 "com.mirego.trikot:trikotFoundation-iosarm32:$trikot_foundation_version"
    tvosx64 "com.mirego.trikot:trikotFoundation-tvosx64:$trikot_foundation_version"
    tvosarm64 "com.mirego.trikot:trikotFoundation-tvosarm64:$trikot_foundation_version"
```
## License

Trikot.foundation is © 2018-2019 [Mirego](https://www.mirego.com) and may be freely distributed under the [New BSD license](http://opensource.org/licenses/BSD-3-Clause). See the [`LICENSE.md`](https://github.com/mirego/trikot.foundation/blob/master/LICENSE.md) file.

## About Mirego

[Mirego](https://www.mirego.com) is a team of passionate people who believe that work is a place where you can innovate and have fun. We’re a team of [talented people](https://life.mirego.com) who imagine and build beautiful Web and mobile applications. We come together to share ideas and [change the world](http://www.mirego.org).

We also [love open-source software](https://open.mirego.com) and we try to give back to the community as much as we can.
