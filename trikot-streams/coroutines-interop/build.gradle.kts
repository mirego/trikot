plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("mirego.publish")
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://plugins.gradle.org/m2/")
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

kotlin {
    android {
        publishAllLibraryVariants()
    }
    jvm()
    ios()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings {
                optIn("kotlinx.coroutines.InternalCoroutinesApi")
                optIn("kotlin.experimental.ExperimentalTypeInference")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(project(Project.TRIKOT_STREAMS))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}")
                implementation("org.jetbrains.kotlinx:atomicfu:${Versions.ATOMIC_FU}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLINX_COROUTINES}")
            }
        }
    }
}

android {
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK
    }
}
