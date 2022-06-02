plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

version = "1.0"

kotlin {
    configureKmmTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Project.TRIKOT_KWORD))
                implementation(project(Project.TRIKOT_STREAMS))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(Project.TRIKOT_STREAMS_TEST_UTILS))
                implementation("org.jetbrains.kotlin:kotlin-test-common")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
        }

        val androidTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }

        val iosArm32Main by getting {
            dependsOn(iosMain)
        }

        val iosArm64Main by getting {
            dependsOn(iosMain)
        }

        val iosX64Main by getting {
            dependsOn(iosMain)
        }

        val tvosArm64Main by getting {
            dependsOn(iosMain)
        }

        val tvosX64Main by getting {
            dependsOn(iosMain)
        }

        val watchos32Main by creating {
            dependsOn(iosMain)
        }

        val watchosArm32Main by getting {
            dependsOn(iosMain)
        }

        val watchosArm64Main by getting {
            dependsOn(iosMain)
        }

        val watchosX64Main by getting {
            dependsOn(iosMain)
        }

        val macosX64Main by getting {
            dependsOn(iosMain)
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
