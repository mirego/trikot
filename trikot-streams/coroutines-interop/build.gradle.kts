plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("mirego.publish")
}

group = "com.mirego.trikot.streams"

kotlin {
    configureKmmTargets(js = false)

    sourceSets {
        all {
            languageSettings {
                optIn("kotlinx.coroutines.InternalCoroutinesApi")
                optIn("kotlin.experimental.ExperimentalTypeInference")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlinx.coroutines.DelicateCoroutinesApi")
                optIn("kotlinx.coroutines.FlowPreview")
                optIn("kotlin.contracts.ExperimentalContracts")
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

        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

        val androidTest by getting {
            dependsOn(jvmTest)
        }

        val nativeTest by creating {
            dependsOn(commonTest)
        }

        val iosX64Test by getting {
            dependsOn(nativeTest)
        }

        val iosSimulatorArm64Test by getting {
            dependsOn(nativeTest)
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
