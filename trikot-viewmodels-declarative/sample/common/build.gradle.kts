plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("mirego.kword").version("2.0.1")
}

group = "com.mirego.sample"

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

fun org.jetbrains.kotlin.gradle.plugin.mpp.Framework.configureFramework() {
    baseName = Project.TRIKOT_SAMPLES_FRAMEWORK_NAME
    transitiveExport = true
    export(project(Project.TRIKOT_VIEWMODELS_DECLARATIVE))
    export(project(Project.TRIKOT_STREAMS))
    export(project(Project.TRIKOT_FOUNDATION))
    export(project(Project.TRIKOT_HTTP))
    export(project(Project.TRIKOT_KWORD))
}

kotlin {
    jvmToolchain(Versions.JVM_TOOLCHAIN)
    androidTarget()

    cocoapods {
        name = Project.TRIKOT_SAMPLES_FRAMEWORK_NAME
        summary = "Trikot-viewmodels-declarative sample"
        homepage = "www.mirego.com"
        license = "BSD-3"
        extraSpecAttributes = mutableMapOf(
            "resources" to "\"src/commonMain/resources/translations/*\"",
            "prepare_command" to """
                <<-CMD
                    ../../../gradlew :trikot-viewmodels-declarative:sample:common:generateDummyFramework
                CMD
            """.trimIndent()
        )

        framework {
            configureFramework()
        }
    }

    ios()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.Experimental")
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

        val iosMain by getting {
            dependsOn(commonMain)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }
}

android {
    namespace = "com.mirego.sample"
    defaultConfig {
        compileSdk = Versions.Android.COMPILE_SDK
        minSdk = Versions.Android.MIN_SDK
    }
}

project.afterEvaluate {
    tasks
        .filter { task -> task.name.startsWith("compile") && task.name.contains("Kotlin") }
        .forEach { task ->
            task.dependsOn(tasks.withType<com.mirego.kword.KWordEnumGenerate>())
        }
}
