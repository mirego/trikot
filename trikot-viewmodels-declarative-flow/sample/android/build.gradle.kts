plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.mirego.sample"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK

        applicationId = "com.mirego.sample"
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

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs +
            listOf(
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
            )
    }

    sourceSets {
        getByName("main") {
            resources.srcDir("../common/src/commonMain/resources/")
        }
    }

    lint {
        abortOnError = true
        checkReleaseBuilds = true
        disable.add("NotificationPermission")
    }
}

dependencies {
    implementation(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE_SAMPLE_COMMON_FLOW))
    implementation(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE_COMPOSE_FLOW))
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-common-java8:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.core:core-ktx:1.7.0")
}
