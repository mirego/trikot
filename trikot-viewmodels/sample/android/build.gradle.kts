plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK

        applicationId = "com.trikot.viewmodels.sample"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
        }
    }
    packagingOptions {
        resources {
            excludes += setOf("META-INF/*.kotlin_module")
            pickFirsts += setOf(
                "META-INF/androidx.customview_customview.version",
                "META-INF/androidx.legacy_legacy-support-core-ui.version"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    lint {
        abortOnError = true
        checkReleaseBuilds = true
        disable.add("NotificationPermission")
    }
}

configurations.forEach { it.exclude("org.reactivestreams") }

dependencies {
    api(project(Project.TRIKOT_VIEWMODELS_SAMPLE_COMMON))

    implementation("androidx.appcompat:appcompat:1.3.0-alpha02")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("com.google.android.material:material:1.2.1")
    implementation("com.squareup.picasso:picasso:2.71828")
}

ktlint {
    android.set(true)
}
