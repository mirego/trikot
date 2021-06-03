plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    defaultConfig {
        compileSdk = 30
        minSdk = 23
        targetSdk = 30

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.jetpackCompose
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(project(":sample:common"))
    implementation(project(":compose"))
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.1")
    implementation("com.google.android.material:material:1.3.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.core:core-ktx:1.5.0")
}

configurations.all {
    exclude(group = "org.reactivestreams")
}
