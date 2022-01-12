plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    defaultConfig {
        compileSdk = 31
        minSdk = 23
        targetSdk = 31

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
    implementation(project(Dependencies.TRIKOT_VIEWMODELS_DECLARATIVE_SAMPLE_COMMON))
    implementation(project(Dependencies.TRIKOT_VIEWMODELS_DECLARATIVE_COMPOSE))
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.lifecycle:lifecycle-extensions:${Versions.ANDROIDX_LIFECYCLE_EXTENSIONS}")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-common-java8:${Versions.ANDROIDX_LIFECYCLE}")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.core:core-ktx:1.7.0")
}

configurations.all {
    exclude(group = "org.reactivestreams")
}
