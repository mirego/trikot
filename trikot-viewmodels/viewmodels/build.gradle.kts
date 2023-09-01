plugins {
    kotlin("multiplatform")
    kotlin("kapt")
    id("com.android.library")
    id("mirego.publish")
}

group = "com.mirego.trikot"

kotlin {
    configureKmmTargets()

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

        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
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
                implementation("androidx.lifecycle:lifecycle-process:${Versions.ANDROIDX_LIFECYCLE}")
                implementation("androidx.recyclerview:recyclerview:1.1.0")
                implementation("androidx.appcompat:appcompat:1.2.0")
                implementation("com.squareup.picasso:picasso:2.71828")
                implementation("com.google.android.material:material:1.3.0")
                implementation("javax.annotation:javax.annotation-api:1.3.2")
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation("androidx.test:core:1.4.1-alpha03")
                implementation("androidx.test.ext:junit:1.1.3")
                implementation("org.robolectric:robolectric:4.9.2")
                implementation("androidx.fragment:fragment-testing:1.4.0")
            }
        }

        val iosMain by getting

        val tvosMain by getting {
            dependsOn(iosMain)
        }
    }
}

android {
    namespace = "com.mirego.trikot.viewmodels.android"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }

    sourceSets {
        getByName("test") {
            manifest {
                srcFile("src/androidUnitTest/AndroidManifest.xml")
            }
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}
