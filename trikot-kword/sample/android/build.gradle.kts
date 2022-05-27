plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.JETPACK_COMPOSE
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    sourceSets {
        getByName("main") {
            resources.srcDir("../common/src/commonMain/resources/")
        }
    }
}

dependencies {
    implementation(project(Project.TRIKOT_KWORD))
    implementation(project(Project.TRIKOT_KWORD_SAMPLE_COMMON))
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.core:core-ktx:1.7.0")

    val composeVersion = "1.2.0-alpha08"
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
}
