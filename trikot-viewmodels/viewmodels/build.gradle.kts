plugins {
    kotlin("multiplatform")
    kotlin("kapt")
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
    android {
        publishAllLibraryVariants()
    }

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

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Project.TRIKOT_FOUNDATION))
                api(project(Project.TRIKOT_STREAMS))
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
                implementation("androidx.lifecycle:lifecycle-process:${Versions.ANDROIDX_LIFECYCLE}")
                implementation("androidx.lifecycle:lifecycle-extensions:${Versions.ANDROIDX_LIFECYCLE_EXTENSIONS}")
                implementation("androidx.recyclerview:recyclerview:1.1.0")
                implementation("androidx.appcompat:appcompat:1.2.0")
                implementation("com.squareup.picasso:picasso:2.71828")
                implementation("com.google.android.material:material:1.3.0")
                implementation("javax.annotation:javax.annotation-api:1.3.2")
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation("androidx.test:core:1.3.0")
                implementation("androidx.test.ext:junit:1.1.2")
                implementation("org.robolectric:robolectric:4.5.1")
                implementation("androidx.fragment:fragment-testing:1.3.2")
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
        }

        val iosArm32Main by getting {
            dependsOn(iosMain)
        }

        val iosArm64Main by getting {
            dependsOn(iosMain)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }

        val iosX64Main by getting {
            dependsOn(iosMain)
        }

        val tvosMain by getting {
            dependsOn(commonMain)
        }

        val watchosArm64Main by getting {
            dependsOn(commonMain)
        }

        val watchosArm32Main by getting {
            dependsOn(commonMain)
        }

        val watchosX64Main by getting {
            dependsOn(commonMain)
        }

        val macosX64Main by getting {
            dependsOn(commonMain)
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
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }

    sourceSets {
        getByName("test") {
            manifest {
                srcFile("src/androidTest/AndroidManifest.xml")
            }
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

// Fixes w: library included more than once: ~/.konan/kotlin-native-prebuilt-macos-XXX/klib/common/stdlib
afterEvaluate {
    val compilations = listOf("iosMain", "tvosMain").map {
        kotlin.targets["metadata"].compilations[it]
    }
    compilations.forEach { compilation ->
        compilation.compileKotlinTask.doFirst {
            compilation.compileDependencyFiles = files(
                compilation.compileDependencyFiles.filterNot { it.absolutePath.endsWith("klib/common/stdlib") }
            )
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

release {
    checkTasks = listOf("check")
    buildTasks = listOf("publish")
    updateVersionPart = 2
}
