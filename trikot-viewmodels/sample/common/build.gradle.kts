plugins {
    id("kotlin-multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")
    id("kotlinx-serialization")
    id("maven-publish")
}

group = "com.trikot.viewmodels.sample"
version = "0.0.1"

android {
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK
    }
}

fun org.jetbrains.kotlin.gradle.plugin.mpp.Framework.configureFramework() {
    baseName = Project.TRIKOT_SAMPLES_FRAMEWORK_NAME
    transitiveExport = true
    export(project(Project.TRIKOT_FOUNDATION))
    export(project(Project.TRIKOT_STREAMS))
    export(project(Project.TRIKOT_VIEWMODELS))
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    cocoapods {
        name = Project.TRIKOT_SAMPLES_FRAMEWORK_NAME
        summary = "Trikot-viewmodels sample"
        homepage = "www.mirego.com"
        license = "BSD-3"
        extraSpecAttributes = mutableMapOf(
            "prepare_command" to """
                <<-CMD
                    ../../../gradlew :trikot-viewmodels:sample:common:generateDummyFramework
                CMD
            """.trimIndent()
        )

        framework {
            configureFramework()
        }
    }

    ios {
        binaries.framework {
            configureFramework()
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            configureFramework()
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

        val iosMain by getting {
            dependsOn(commonMain)
        }
    }
}
