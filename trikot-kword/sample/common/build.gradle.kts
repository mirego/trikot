plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jlleitschuh.gradle.ktlint")
    id("mirego.kword").version("2.0.1")
}

group = "com.mirego.sample"

val frameworkName = "TRIKOT_FRAMEWORK_NAME"

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

fun org.jetbrains.kotlin.gradle.dsl.AbstractKotlinNativeBinaryContainer.configureFramework() {
    framework {
        baseName = frameworkName
        transitiveExport = true
        export(project(Project.TRIKOT_KWORD))
        export(project(Project.TRIKOT_KWORD_FLOW))
    }
}

kotlin {
    android()

    ios {
        binaries.configureFramework()
    }

    iosSimulatorArm64 {
        binaries.configureFramework()
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.Experimental")
            }
        }

        val commonMain by getting {
            dependencies {
                api(project(Project.TRIKOT_KWORD))
                api(project(Project.TRIKOT_KWORD_FLOW))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}")
            }
            kotlin.srcDir(kword.generatedDir)
        }

        val androidMain by getting {
            dependsOn(commonMain)
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

val copyFramework by tasks.creating {
    val buildType = project.findProperty("kotlin.build.type")?.toString() ?: "RELEASE"
    val target = project.findProperty("kotlin.target")?.toString() ?: "iosArm64"
    val targetDir = project.findProperty("configuration.build.dir")?.toString() ?: "build/bin/ios"
    val translationDir = "$projectDir/../common/src/commonMain/resources/translations"
    val framework =
        kotlin.targets.findByName(target)?.let {
            it as? org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
        }?.binaries?.getFramework(
            buildType
        ) ?: return@creating

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
