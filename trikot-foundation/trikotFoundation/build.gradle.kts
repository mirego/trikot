plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("mirego.publish")
}

apply(plugin = "kotlinx-atomicfu")

group = "com.mirego.trikot"

kotlin {
    configureKmmTargets(js = false)
    js(BOTH) {
        browser()
    }

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
                implementation("com.jakewharton.threetenabp:threetenabp:1.2.1")
            }
        }

        val jvmMain by getting {
            dependsOn(jvmShared)
        }

        val jvmTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

        val androidMain by getting {
            dependsOn(jvmShared)
        }

        val androidTest by getting {
            dependsOn(jvmTest)
        }

        val jsMain by getting {
            dependsOn(commonMain)
        }

        val jsTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-js")
            }
        }

        val nativeMain by creating {
            dependsOn(commonMain)
        }

        val nativeTest by creating {
            dependsOn(commonTest)
        }

        val iosMain by getting {
            dependsOn(nativeMain)
        }

        val iosArm32Main by getting {
            dependsOn(nativeMain)
        }

        val iosArm64Main by getting {
            dependsOn(nativeMain)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(iosArm64Main)
        }

        val iosX64Main by getting {
            dependsOn(nativeMain)
        }

        val tvosMain by getting {
            dependsOn(nativeMain)
        }

        val tvosArm64Main by getting {
            dependsOn(tvosMain)
        }

        val tvosX64Main by getting {
            dependsOn(tvosMain)
        }

        val watchos32Main by creating {
            dependsOn(nativeMain)
        }

        val watchosArm32Main by getting {
            dependsOn(watchos32Main)
        }

        val watchosArm64Main by getting {
            dependsOn(watchos32Main)
        }

        val watchosX64Main by getting {
            dependsOn(nativeMain)
        }

        val macosX64Main by getting {
            dependsOn(nativeMain)
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
