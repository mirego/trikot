plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("mirego.kword").version("5.5.0-SNAPSHOT")
//    id("mirego.kword").version("5.4.0")
}

group = "com.mirego.sample"

val frameworkName = "TRIKOT_FRAMEWORK_NAME"

configurations {
    create("testApi") {}
    create("testDebugApi") {}
    create("testReleaseApi") {}
}

kword {
    translationFile.set(file("src/commonMain/resources/translations/translation.en.json"))
    enumClassName.set("com.mirego.sample.KWordTranslation")
    generatedDir.set(file("src/commonMain/generated"))
}

fun org.jetbrains.kotlin.gradle.plugin.mpp.Framework.configureFramework() {
    baseName = Project.TRIKOT_SAMPLES_FRAMEWORK_NAME
    transitiveExport = true
    isStatic = false
    export(project(Project.TRIKOT_KWORD))
    export(project(Project.TRIKOT_KWORD_FLOW))
}

kotlin {
    jvmToolchain(Versions.JVM_TOOLCHAIN)
    androidTarget()

    cocoapods {
        name = Project.TRIKOT_SAMPLES_FRAMEWORK_NAME
        summary = "Trikot-kword sample"
        homepage = "www.mirego.com"
        license = "BSD-3"
        podfile = project.file("../ios/Podfile")
        extraSpecAttributes = mutableMapOf(
            "resources" to "\"src/commonMain/resources/translations/*\"",
            "prepare_command" to """
                <<-CMD
                    ../../../gradlew :trikot-kword:sample:common:generateDummyFramework
                CMD
            """.trimIndent()
        )

        framework {
            configureFramework()
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(Project.TRIKOT_KWORD))
                api(project(Project.TRIKOT_KWORD_FLOW))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}")
            }
            kotlin.srcDir(kword.generatedDir)
        }
    }
}

android {
    namespace = "com.mirego.kword.sample"
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
