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
}

group = "com.mirego.trikot"

kotlin {
    android {
        publishAllLibraryVariants()
    }
    jvm()
    ios()
    iosArm32("iosArm32")
    tvos()
    watchos()
    macosX64()
    js(BOTH) {
        browser()
    }

    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
        }

        val commonMain by getting

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
