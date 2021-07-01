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
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://jitpack.io")
    maven("https://plugins.gradle.org/m2/")
    maven("https://s3.amazonaws.com/mirego-maven/public")
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
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.mirego.trikot:trikotFoundation:${project.extra["trikot_foundation_version"]}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
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
                implementation("org.jetbrains.kotlin-wrappers:kotlin-extensions:${project.extra["kotlin_wrappers_extensions_version"]}-kotlin-${project.extra["kotlin_version"]}")
            }
        }

        val jsTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-js")
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${project.extra["kotlinx_serialization_version"]}")
            }
        }

        val androidTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
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
