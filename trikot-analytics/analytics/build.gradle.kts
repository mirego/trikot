plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("mirego.release").version("2.0")
    id("mirego.publish").version("1.0")
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://plugins.gradle.org/m2/")
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

group = "com.mirego.trikot"

kotlin {
    jvm()
    ios()
    iosArm32("iosArm32")
    iosSimulatorArm64()
    tvos()
    watchos()
    macosX64()
    js(IR) {
        browser()
    }
    android {
        publishAllLibraryVariants()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Dependencies.TRIKOT_FOUNDATION))
                api(project(Dependencies.TRIKOT_STREAMS))
            }
        }

        val jvmMain by getting {
            dependsOn(commonMain)
        }

        val jsMain by getting {
            dependsOn(commonMain)
        }

        val androidMain by getting {
            dependsOn(commonMain)
        }

        val nativeMain by creating {
            dependsOn(commonMain)
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

        val tvosArm64Main by getting {
            dependsOn(nativeMain)
        }

        val tvosX64Main by getting {
            dependsOn(nativeMain)
        }

        val watchos32Main by creating {
            dependsOn(nativeMain)
        }

        val watchosArm64Main by getting {
            dependsOn(nativeMain)
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
        compileSdkVersion(30)
        minSdkVersion(14)
        targetSdkVersion(30)
    }
}

release {
    checkTasks = listOf("check")
    buildTasks = listOf("publish")
    updateVersionPart = 2
}
