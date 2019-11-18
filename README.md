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
Trikot threading model
See: [Dispatch Queues](./documentation/DISPATCH_QUEUES.md) 

## Installation
##### Import dependencies
```groovy
    api "com.mirego.trikot:foundation:$trikot_foundation_version"
    jvm "com.mirego.trikot:foundation-jvm:$trikot_foundation_version"
    js "com.mirego.trikot:foundation-js:$trikot_foundation_version"
    iosx64 "com.mirego.trikot:foundation-iosx64:$trikot_foundation_version"
    iosarm64 "com.mirego.trikot:foundation-iosarm64:$trikot_foundation_version"
```
