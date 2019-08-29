# Trikot.http Android Kotlin Extensions

Add dependency
```groovy
dependencies {
    implementation "com.mirego.trikot.http:android-ktx:$trikot_http_android_ktx_version"
}
```

##### Setup HTTPRequestFactory Implementation based on [Ktor](https://github.com/ktorio/ktor)
Trikot.http android-ktx provides its own implementation based on `Ktor`

```kotlin
class MyAndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        HttpConfiguration().httpRequestFactory = KtorHttpRequestFactory()
    }
}
```

# Setup connectivity publisher
```kotlin
class MyAndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        HttpConfiguration.connectivityPublisher = AndroidConnectivityPublisher(this).distinctUntilChanged()
    }
}
```
