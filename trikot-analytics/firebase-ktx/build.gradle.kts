import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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
    implementation("com.google.firebase:firebase-analytics:19.0.2")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.ANDROIDX_LIFECYCLE}")
}

android {
    namespace = "com.mirego.trikot.analytics.firebase.ktx"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("firebaseAar") {
                from(components["release"])
                artifactId = "firebase-ktx"
            }
        }
    }
}
