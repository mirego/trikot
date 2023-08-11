plugins {
    kotlin("multiplatform")
    id("mirego.publish")
}

group = "com.mirego.trikot.trikotFoundation"

kotlin {
    configureKmmTargets(android = false)

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Project.TRIKOT_FOUNDATION))
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
