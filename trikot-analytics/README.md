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

#### Add more service
Implement [AnalyticsService](https://github.com/mirego/trikot.analytics/blob/master/analytics/src/commonMain/kotlin/com/mirego/trikot/analytics/AnalyticsService.kt)


## Common
##### Import dependencies

Use the code bellow to import the core module of trikot analytics.
```groovy
    maven { url('https://s3.amazonaws.com/mirego-maven/public') }

    api "com.mirego.trikot:analytics:$trikot_analytics_version"
```

If you wish to use the `trikot.viewmodel` integration, you have to import it from a separate module.
```groovy
    api "com.mirego.trikot:analytics-viewmodel:$trikot_analytics_version"
```

## License

Trikot.analytics is © 2018-2019 [Mirego](https://www.mirego.com) and may be freely distributed under the [New BSD license](http://opensource.org/licenses/BSD-3-Clause). See the [`LICENSE.md`](https://github.com/mirego/trikot.analytics/blob/master/LICENSE.md) file.

## About Mirego

[Mirego](https://www.mirego.com) is a team of passionate people who believe that work is a place where you can innovate and have fun. We’re a team of [talented people](https://life.mirego.com) who imagine and build beautiful Web and mobile applications. We come together to share ideas and [change the world](http://www.mirego.org).

We also [love open-source software](https://open.mirego.com) and we try to give back to the community as much as we can.
