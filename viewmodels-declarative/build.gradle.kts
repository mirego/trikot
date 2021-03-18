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
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://jitpack.io")
    maven("https://plugins.gradle.org/m2/")
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

group = "com.mirego.trikot"

// Temporary workaround for issue with AGP+MPP (https://youtrack.jetbrains.com/issue/KT-43944)
configurations {
    create("testApi") {}
    create("testDebugApi") {}
    create("testReleaseApi") {}
}

kotlin {
    android {
        publishAllLibraryVariants()
    }

    jvm()
    ios()
    iosArm32("iosArm32")
    tvos()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.mirego.trikot:trikotFoundation:${project.extra["trikot_foundation_version"]}")
                implementation("com.mirego.trikot:streams:${project.extra["trikot_streams_version"]}")
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
                implementation("org.jetbrains.kotlin:kotlin-reflect:${project.extra["kotlin_version"]}")
                implementation("androidx.compose.ui:ui:${project.extra["compose_version"]}")
                implementation("androidx.compose.ui:ui-tooling:${project.extra["compose_version"]}")
                implementation("androidx.compose.runtime:runtime-livedata:${project.extra["compose_version"]}")
                implementation("androidx.compose.runtime:runtime-rxjava2:${project.extra["compose_version"]}")
                implementation("androidx.compose.foundation:foundation:${project.extra["compose_version"]}")
                implementation("androidx.compose.material:material:${project.extra["compose_version"]}")
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha03")
                implementation("androidx.activity:activity-compose:1.3.0-alpha04")
                implementation("androidx.lifecycle:lifecycle-process:${project.extra["androidx_lifecycle_version"]}")
                implementation("androidx.appcompat:appcompat:1.2.0")
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
    }
}

android {
    defaultConfig {
        compileSdkVersion(30)
        minSdkVersion(21)
        targetSdkVersion(30)
    }

    buildFeatures {
        compose = true
    }
    compileOptions {
        // Setting VERSION_11 here causes an error, waiting for AGP fix. https://issuetracker.google.com/issues/181770616
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${project.extra["compose_version"]}"
    }
}

// kotlinOptions is weirdly unavailable in Kotlin without a cast. AGP bug?
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

release {
    checkTasks = listOf("check")
    buildTasks = listOf("publish")
    updateVersionPart = 2
}
