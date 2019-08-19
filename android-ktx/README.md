# Trikot.http Android Kotlin extensions

Add dependency
```groovy
dependencies {
    implementation "com.mirego.trikot.http:android-ktx:$trikot_http_android_ktx_version"
}
```

# Setup connectivity publisher

class MyAndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        HttpConfiguration.connectivityPublisher = AndroidConnectivityPublisher(this).distinctUntilChanged()
    }
}
