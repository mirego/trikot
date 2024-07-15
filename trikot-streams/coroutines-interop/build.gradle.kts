plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("mirego.publish")
    id("org.jetbrains.kotlinx.atomicfu")
}

group = "com.mirego.trikot.streams"

kotlin {
    configureKmmTargets(js = false)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Project.TRIKOT_STREAMS))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}")
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

        val androidUnitTest by getting {
            dependsOn(jvmTest)
        }
    }
}

android {
    namespace = "com.mirego.trikot.streams.coroutines"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
    }
}
