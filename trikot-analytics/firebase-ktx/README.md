# Trikot.analytics Android Firebase extensions

### Installation
Add dependency
```groovy
dependencies {
    implementation "com.mirego.trikot.analytics:firebase-ktx:$trikot_analytics_firebase_ktx_version"
}
```

### Configure
```kotlin
    val context = this // application context
    AnalyticsConfiguration.analyticsManager =   FirebaseAnalyticsService(this)
```
