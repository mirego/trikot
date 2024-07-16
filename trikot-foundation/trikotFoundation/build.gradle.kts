plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("mirego.publish")
    id("org.jetbrains.kotlinx.atomicfu")
}

group = "com.mirego.trikot"

kotlin {
    configureKmmTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:atomicfu:${Versions.ATOMIC_FU}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
            }
        }

        val jvmShared by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("com.jakewharton.threetenabp:threetenabp:1.4.6")
            }
        }

        val jvmMain by getting {
            dependsOn(jvmShared)
        }

        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

        val androidMain by getting {
            dependsOn(jvmShared)
        }

        val androidUnitTest by getting {
            dependsOn(jvmTest)
        }

        val jsTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-js")
            }
        }

        val nativeShared by creating {
            dependsOn(commonMain)
        }

        val iosMain by getting {
            dependsOn(nativeShared)
        }

        val macosMain by getting {
            dependsOn(nativeShared)
        }

        val watchosMain by getting {
            dependsOn(nativeShared)
        }

        val tvosMain by getting {
            dependsOn(nativeShared)
        }
    }
}

android {
    namespace = "com.mirego.trikot.foundation"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
    }
}
