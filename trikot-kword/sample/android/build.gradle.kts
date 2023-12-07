plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.mirego.kword.sample.android"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK

        applicationId = "com.mirego.kword.sample"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isDebuggable = true
            isMinifyEnabled = false
        }

        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = false
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.JETPACK_COMPOSE_COMPILER
    }

    sourceSets {
        getByName("main") {
            resources.srcDir("../common/src/commonMain/resources/")
        }
    }
}

dependencies {
    implementation(project(Project.TRIKOT_KWORD))
    implementation(project(Project.TRIKOT_KWORD_REMOTE_UPDATE))
    implementation(project(Project.TRIKOT_KWORD_FLOW))
    implementation(project(Project.TRIKOT_KWORD_SAMPLE_COMMON))
    implementation("com.squareup.okio:okio:3.2.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.core:core-ktx:1.7.0")

    val composeVersion = "1.2.0-alpha08"
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
}
