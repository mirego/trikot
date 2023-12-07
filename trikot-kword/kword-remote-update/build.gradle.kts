plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("mirego.publish")
}

group = "com.mirego.trikot"

kotlin {
    configureKmmTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Project.TRIKOT_KWORD))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLINX_SERIALIZATION}")
                implementation("io.ktor:ktor-client-core:${Versions.KTOR}")
                implementation("com.squareup.okio:okio:${Versions.OKIO}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(Project.TRIKOT_STREAMS_TEST_UTILS))
                implementation("org.jetbrains.kotlin:kotlin-test-common")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
                implementation("com.squareup.okio:okio-fakefilesystem:${Versions.OKIO}")
            }
        }

        val jvmMain by getting {
            dependsOn(commonMain)
        }

        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

        val jsMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-extensions:${Versions.KOTLIN_WRAPPERS_EXTENSIONS}")
            }
        }

        val jsTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-js")
            }
        }

        val androidMain by getting {
            dependsOn(jvmMain)
            dependencies {
                implementation("io.ktor:ktor-client-android:${Versions.KTOR}")
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

        val appleMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:${Versions.KTOR}")
            }
        }
    }
}

android {
    namespace = "com.mirego.trikot.kword"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
    }
}
