plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    id("mirego.kword").version("3.0.0")
}

repositories {
    google()
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

group = "com.mirego.sample"
version = "1.0.0"

configurations {
    create("testApi") {}
    create("testDebugApi") {}
    create("testReleaseApi") {}
}

kword {
    translationFile = file("src/commonMain/resources/translations/translation.en.json")
    enumClassName = "com.mirego.sample.KWordTranslation"
    generatedDir = file("src/commonMain/generated")
}

kotlin {
    android()

    cocoapods {
        framework {
            summary = "Trikot-viewmodels-declarative sample"
            homepage = "www.mirego.com"
            baseName = Project.TRIKOT_SAMPLES_FRAMEWORK_NAME
            transitiveExport = true
            embedBitcode("bitcode")

            export(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE))
            export(project(Project.TRIKOT_STREAMS))
            export(project(Project.TRIKOT_FOUNDATION))
            export(project(Project.TRIKOT_HTTP))
            export(project(Project.TRIKOT_KWORD))
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries {
            framework {
                baseName = Project.TRIKOT_SAMPLES_FRAMEWORK_NAME
                transitiveExport = true
                export(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE))
                export(project(Project.TRIKOT_STREAMS))
                export(project(Project.TRIKOT_FOUNDATION))
                export(project(Project.TRIKOT_HTTP))
                export(project(Project.TRIKOT_KWORD))
            }
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
            }
        }

        val commonMain by getting {
            dependencies {
                api(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE))
                api(project(Project.TRIKOT_FOUNDATION))
                api(project(Project.TRIKOT_STREAMS))
                api(project(Project.TRIKOT_HTTP))
                api(project(Project.TRIKOT_KWORD))
            }
            kotlin.srcDir(kword.generatedDir)
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROIDX_LIFECYCLE}")
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
        targetSdk = Versions.Android.TARGET_SDK
    }
}

project.afterEvaluate {
    tasks
        .filter { task -> task.name.startsWith("compile") && task.name.contains("Kotlin") }
        .forEach { task ->
            task.dependsOn(tasks.withType<com.mirego.kword.KWordEnumGenerate>())
        }
}
