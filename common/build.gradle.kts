import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

buildscript {
    dependencies {
        classpath("com.github.nbaztec:coveralls-jacoco-gradle-plugin:1.2.5") {
            // The plugin is using an older version (1.3.72) which prevent us from using 1.4+
            // https://github.com/nbaztec/coveralls-jacoco-gradle-plugin/blob/main/build.gradle.kts#L35
            exclude(group = "org.jetbrains.kotlin", module = "kotlin-gradle-plugin")
        }
    }
}

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
    id("mirego.kword") version Versions.TRIKOT_KWORD_PLUGIN
    id("jacoco")
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.12"
}

repositories {
    google()
    jcenter()
    mavenCentral()
    mavenLocal()
    maven(url = "https://kotlin.bintray.com/kotlinx")
    maven(url = "https://jitpack.io")
    maven(url = "https://s3.amazonaws.com/mirego-maven/public")
}

android {
    compileSdkVersion(Versions.COMPILE_SDK)
    defaultConfig {
        minSdkVersion(Versions.MIN_SDK)
    }
    sourceSets {
        val main by getting {
            resources.srcDir("src/commonMain/resources/")
        }
    }
}

kword {
    translationFile("src/commonMain/resources/translations/translation.en.json")
    enumClassName("com.trikot.sample.localization.KWordTranslation")
    generatedDir("src/commonMain/generated")
}

kotlin {
    android {
        publishAllLibraryVariants()
    }

    ios {
        binaries {
            framework {
                embedBitcode("disable")
                baseName = Const.TRIKOT_FRAMEWORK_NAME
                transitiveExport = true
                export(SharedLibs.Trikot.Foundation)
                export(SharedLibs.Trikot.Streams)
                export(SharedLibs.Trikot.Viewmodels)
                export(SharedLibs.Trikot.Http)
                export(SharedLibs.Trikot.Kword)
            }
        }
    }

    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.Experimental")
            languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
        }

        val commonMain by getting {
            dependencies {
                api(SharedLibs.Trikot.Foundation)
                api(SharedLibs.Trikot.Streams)
                api(SharedLibs.Trikot.Viewmodels)
                api(SharedLibs.Trikot.Http)
                api(SharedLibs.Trikot.Kword)
                implementation(Libs.Kotlinx.SerializationJson)
            }
            kotlin.srcDir(kword.generatedDir)
        }

        val commonTest by getting {
            dependencies {
                implementation(Libs.Kotlin.TestCommon)
                implementation(Libs.Kotlin.TestAnnotationCommon)
                implementation(Libs.Mockk.Common)
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(Libs.AndroidX.LifecycleViewModel)
                implementation(Libs.AndroidX.LifecycleViewModelKtx)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(Libs.Kotlin.Test)
                implementation(Libs.Kotlin.TestJUnit)
                implementation(Libs.Mockk.Mockk)
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
        }
    }
}

// This task attaches native framework built from ios module to Xcode project
// (see iosApp directory). Don't run this task directly,
// Xcode runs this task itself during its build process.
// Before opening the project from iosApp directory in Xcode,
// make sure all Gradle infrastructure exists (gradle.wrapper, gradlew).
val copyFramework by tasks.creating {
    val buildType = project.findProperty("kotlin.build.type")?.toString() ?: "RELEASE"
    val target = project.findProperty("kotlin.target")?.toString() ?: "iosArm64"
    val kotlinNativeTarget = kotlin.targets.getByName<KotlinNativeTarget>(target)
    val framework = kotlinNativeTarget.binaries.getFramework(buildType)
    dependsOn(framework.linkTask)

    doLast {
        val srcFile = framework.outputFile
        val targetDir = project.property("configuration.build.dir")
        val frameworkDir = "$targetDir/${Const.TRIKOT_FRAMEWORK_NAME}.framework"
        val translationDir = "$projectDir/../common/src/commonMain/resources/translations"
        copy {
            from(srcFile.parent)
            into(targetDir)
            include("${Const.TRIKOT_FRAMEWORK_NAME}.framework/**")
            include("${Const.TRIKOT_FRAMEWORK_NAME}.framework.dSYM/**")
        }
        copy {
            from(translationDir)
            into(frameworkDir)
            include("**")
        }
    }
}

project.afterEvaluate {
    project.tasks.filter { task -> task.name.startsWith("compile") && task.name.contains("Kotlin") }
        .forEach { task ->
            task.dependsOn("kwordGenerateEnum")
        }
}

jacoco {
    toolVersion = "0.8.2"
    reportsDir = file("build/reports")
}

val jacocoTestReport by tasks.creating(JacocoReport::class) {
    dependsOn("test")
    group = "Reporting"
    description = "Generate Jacoco coverage reports"

    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }

    val excludes: (ConfigurableFileTree) -> Unit = {
        it.exclude(
            "**/serializer.class",
            "**/factories**"
        )
    }
    classDirectories.setFrom(
        listOf(
            fileTree("build/intermediates/classes/debug", excludes),
            fileTree("build/tmp/kotlin-classes/debug", excludes)
        )
    )
    executionData.setFrom(files("build/jacoco/testDebugUnitTest.exec"))
    sourceDirectories.setFrom(files(listOf("src/commonMain/kotlin")))
}

tasks.find { it.name == "coverallsJacoco" }?.mustRunAfter(jacocoTestReport)

coverallsJacoco {
    reportPath = "$buildDir/reports/jacocoTestReport/jacocoTestReport.xml"
    reportSourceSets = files(listOf("src/commonMain/kotlin"))
}
