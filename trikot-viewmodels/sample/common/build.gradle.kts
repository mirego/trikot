plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
    id("maven-publish")
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    maven("https://jitpack.io")
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

group = "com.trikot.viewmodels.sample"
version = "1.0.0"

android {
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK
    }
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    cocoapods {
        framework {
            summary = "Trikot-viewmodels sample"
            homepage = "www.mirego.com"
            baseName = Project.TRIKOT_SAMPLES_FRAMEWORK_NAME
            transitiveExport = true
            embedBitcode("bitcode")

            export(project(Project.TRIKOT_FOUNDATION))
            export(project(Project.TRIKOT_STREAMS))
            export(project(Project.TRIKOT_VIEWMODELS))
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries {
            framework {
                baseName = Project.TRIKOT_SAMPLES_FRAMEWORK_NAME
                transitiveExport = true
                export(project(Project.TRIKOT_FOUNDATION))
                export(project(Project.TRIKOT_STREAMS))
                export(project(Project.TRIKOT_VIEWMODELS))
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Project.TRIKOT_FOUNDATION))
                api(project(Project.TRIKOT_STREAMS))
                api(project(Project.TRIKOT_VIEWMODELS))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.ANDROIDX_LIFECYCLE}")
                implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}")
                implementation("androidx.lifecycle:lifecycle-viewmodel:${Versions.ANDROIDX_LIFECYCLE}")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROIDX_LIFECYCLE}")
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependsOn(commonMain)
        }
    }
}
