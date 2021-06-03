plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jlleitschuh.gradle.ktlint")
    id("mirego.kword").version("2.0.1")
}

repositories {
    google()
    mavenCentral()
    mavenLocal()
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://jitpack.io")
    maven("https://s3.amazonaws.com/mirego-maven/public")
}

group = "com.mirego.sample"
val frameworkName = "TrikotViewmodelsDeclarativeSample"

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
    ios {
        binaries {
            framework {
                embedBitcode("disable")
                baseName = frameworkName
                transitiveExport = true
                export(Dependencies.trikotStreams)
                export(Dependencies.trikotFoundation)
                export(Dependencies.trikotHttp)
                export(Dependencies.trikotKword)
            }
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
                useExperimentalAnnotation("kotlin.time.ExperimentalTime")
            }
        }

        val commonMain by getting {
            dependencies {
                api(project(":viewmodels-declarative"))
                api(Dependencies.trikotFoundation)
                api(Dependencies.trikotStreams)
                api(Dependencies.trikotHttp)
                api(Dependencies.trikotKword)
            }
            kotlin.srcDir(kword.generatedDir)
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
        }
    }
}

android {
    defaultConfig {
        compileSdk = 30
        minSdk = 23
        targetSdk = 30
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
            include("**")
        }

        copy {
            from(translationDir)
            into(frameworkDir)
            include("**")
        }
    }
}
