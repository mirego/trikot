# Trikot.http Android Kotlin extensions

# Sample Usage

class MyAndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ...
        HttpConfiguration.connectivityPublisher = AndroidConnectivityPublisher(this).distinctUntilChanged()
    }
}
