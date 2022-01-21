# Trikot.analytics

Elegant implementation of Multiplatform Analytics in ReactiveStreams.

#### Usage
See platform declaration on how to configure which service is used.

Declare your events
```kotlin
enum class AnalyticsEvents : AnalyticsEvent {
    myAppEvent,
    myClickEvent
}
```

Identify user
```kotlin
AnalyticsService.identifyUser(userId, mapOf("userProperty" to value))
```

Track an event
```kotlin
AnalyticsService.trackEvent(AnalyticsEvents.myAppEvent, mapOf("eventProperty" to value))
```

Track tap action
```kotlin
val button = MutableMetaButton()
button.onTap = TrackableMetaAction(AnalyticsEvents.myClickEvent) {
    // Code to execute on tap
}
```

#### Firebase
iOS - [swift extensions](./swift-extensions/firebase/README.md)
Android - [firebase-ktx](./firebase-ktx/README.md)

#### Mixpanel
iOS - [swift extensions](./swift-extensions/mixpanel/README.md)
Android - [firebase-ktx](./mixpanel-ktx/README.md)

#### Add more service
Implement [AnalyticsService](https://github.com/mirego/trikot/blob/master/trikot-analytics/analytics/src/commonMain/kotlin/com/mirego/trikot/analytics/AnalyticsService.kt)


## Common
##### Import dependencies

Use the code bellow to import the core module of trikot analytics.
```groovy
   dependencies {
       maven { url('https://s3.amazonaws.com/mirego-maven/public') }
   }

    ios() {
        binaries {
            framework {
                export "com.mirego.trikot:analytics:$trikot_version"
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation "com.mirego.trikot:analytics:$trikot_version"
            }
        }
    }
```

If you wish to use the `trikot.viewmodel` integration, you have to import it from a separate module.
```groovy
    implementation "com.mirego.trikot:analytics-viewmodel:$trikot_version"
```
