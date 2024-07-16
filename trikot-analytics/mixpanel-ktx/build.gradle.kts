plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    id("mirego.publish")
}

group = "com.mirego.trikot.analytics"

configurations.forEach { it.exclude("org.reactivestreams") }

dependencies {
    api(project(Project.TRIKOT_ANALYTICS))
    api(project(Project.TRIKOT_FOUNDATION))
    api(project(Project.TRIKOT_STREAMS))
    implementation("com.mixpanel.android:mixpanel-android:5.9.6")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.ANDROIDX_LIFECYCLE}")
}

android {
    namespace = "com.mirego.trikot.analytics.mixpanel.ktx"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("mixpanelAar") {
                from(components["release"])
                artifactId = "mixpanel-ktx"
            }
        }
    }
}
