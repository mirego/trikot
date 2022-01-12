plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    defaultConfig {
        compileSdk = 31
        minSdk = 23
        targetSdk = 31

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
        pickFirst("META-INF/androidx.customview_customview.version")
        pickFirst("META-INF/androidx.legacy_legacy-support-core-ui.version")
        exclude("META-INF/*.kotlin_module")
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    lintOptions {
        isCheckReleaseBuilds = true
        isAbortOnError = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

configurations.forEach { it.exclude("org.reactivestreams") }

dependencies {
    api(project(Project.TRIKOT_VIEWMODELS_SAMPLE_COMMON))

    implementation("androidx.appcompat:appcompat:1.3.0-alpha02")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation("androidx.lifecycle:lifecycle-extensions:${Versions.ANDROIDX_LIFECYCLE_EXTENSIONS}")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("com.google.android.material:material:1.2.1")
    implementation("com.squareup.picasso:picasso:2.71828")
}

ktlint {
    android.set(true)
}
