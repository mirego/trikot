plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("mirego.publish")
}

group = "com.mirego.trikot"

kotlin {
    configureKmmTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Project.TRIKOT_FOUNDATION))
                api(project(Project.TRIKOT_STREAMS))
            }
        }
    }
}

android {
    namespace = "com.mirego.trikot.analytics"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
    }
}
