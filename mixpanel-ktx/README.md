# Trikot.analytics Android Mixpanel extension

### Installation
Add dependency
```groovy
dependencies {
    implementation "com.mirego.trikot.analytics:mixpanel-ktx:$trikot_analytics_mixpanel_ktx_version"
}
```

### Configure
```kotlin
    val context = this // application context
    AnalyticsConfiguration.analyticsManager =   MixpanelAnalyticsService(this)
```
