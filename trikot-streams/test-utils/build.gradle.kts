plugins {
    kotlin("multiplatform")
    id("org.jlleitschuh.gradle.ktlint")
    id("mirego.publish")
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

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Project.TRIKOT_FOUNDATION))
                api(project(Project.TRIKOT_STREAMS))
                implementation("org.jetbrains.kotlin:kotlin-test-common")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-js")
            }
        }
    }
}
