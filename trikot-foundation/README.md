# Trikot.foundation
Kotlin Multiplatform implementation of threads, timers, atomic references, string utilities and iso8601 dates.

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

## Strings
Multiplatform string extensions for formating, normalizing

```kotlin
val string = "Où sont les bûches de Noël durant l'été?".normalize()
string == "Ou sont les buches de Noel durant l'ete?" // true
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
build.gradle
```groovy
   dependencies {
       maven { url('https://s3.amazonaws.com/mirego-maven/public') }
   }

    ios() {
        binaries {
            framework {
                export "com.mirego.trikot:trikotFoundation:$trikot_version"
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation "com.mirego.trikot:trikotFoundation:$trikot_version"
            }
        }
    }
```
