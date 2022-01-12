plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
    id("maven-publish")
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    maven("https://jitpack.io")
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

group = "com.trikot.viewmodels.sample"
version = "0.0.1"

android {
    defaultConfig {
        compileSdk = 30
        minSdk = 21
    }
}

val frameworkName = "TrikotViewmodelsSample"

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    ios {
        binaries {
            framework {
                baseName = frameworkName
                transitiveExport = true
                export(project(Dependencies.TRIKOT_FOUNDATION))
                export(project(Dependencies.TRIKOT_STREAMS))
                export(project(Dependencies.TRIKOT_VIEWMODELS))
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Dependencies.TRIKOT_FOUNDATION))
                api(project(Dependencies.TRIKOT_STREAMS))
                api(project(Dependencies.TRIKOT_VIEWMODELS))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.ANDROIDX_LIFECYCLE}")
                implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}")
                implementation("androidx.lifecycle:lifecycle-extensions:${Versions.ANDROIDX_LIFECYCLE_EXTENSIONS}")
                implementation("androidx.lifecycle:lifecycle-viewmodel:${Versions.ANDROIDX_LIFECYCLE}")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROIDX_LIFECYCLE}")
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
        }
    }
}

val copyFramework by tasks.creating {
    val buildType = project.findProperty("kotlin.build.type")?.toString() ?: "RELEASE"
    val target = project.findProperty("kotlin.target")?.toString() ?: "iosArm64"
    val targetDir = project.findProperty("configuration.build.dir")?.toString() ?: "build/bin/ios"
    val translationDir = "$projectDir/../common/src/commonMain/resources/translations"
    val framework =
        kotlin.targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>(target).binaries.getFramework(
            buildType
        )

    dependsOn(framework.linkTask)

    doLast {
        val frameworkDir = "$targetDir/$frameworkName.framework"
        val srcFile = framework.outputFile

        copy {
            from(srcFile.parent)
            into(targetDir)
            include("$frameworkName.framework/**")
            include("$frameworkName.framework.dSYM/**")
        }

        copy {
            from(translationDir)
            into(frameworkDir)
            include("**")
        }
    }
}
