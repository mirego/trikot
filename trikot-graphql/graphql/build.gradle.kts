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
                implementation(project(Project.TRIKOT_FOUNDATION))
                implementation(project(Project.TRIKOT_STREAMS))
                implementation(project(Project.TRIKOT_HTTP))
                implementation(project(Project.TRIKOT_DATASOURCES_STREAMS))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLINX_SERIALIZATION}")
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
                implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.ANDROIDX_LIFECYCLE}")
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }
    }
}

android {
    namespace = "com.mirego.trikot.graphql"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK
    }
}
