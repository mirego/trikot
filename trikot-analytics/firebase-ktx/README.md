# Trikot.analytics Android Firebase extensions

### Installation
Add dependency
```groovy
dependencies {
    implementation "com.mirego.trikot.analytics:firebase-ktx:$trikot_version"
}
```

### Configure
```kotlin
    val context = this // application context
    AnalyticsConfiguration.analyticsManager =   FirebaseAnalyticsService(this)
```
