import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@OptIn(ExperimentalKotlinGradlePluginApi::class)
fun KotlinMultiplatformExtension.configureKmmTargets(
    js: Boolean = true,
    android: Boolean = true,
    jvm: Boolean = true,
    ios: Boolean = true,
    tvos: Boolean = true,
    watchos: Boolean = true,
    macosx: Boolean = true,
) {
    applyDefaultHierarchyTemplate()
    jvmToolchain(Versions.JVM_TOOLCHAIN)

    if (android) {
        androidTarget {
            publishAllLibraryVariants()
        }
    }
    if (jvm) {
        jvm()
    }
    if (ios) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()
    }
    if (tvos) {
        tvosX64()
        tvosArm64()
        tvosSimulatorArm64()
    }
    if (watchos) {
        watchosX64()
        watchosArm32()
        watchosSimulatorArm64()
    }
    if (macosx) {
        macosX64()
        macosArm64()
    }
    if (js) {
        js(IR) {
            browser()
        }
    }
}
