import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun KotlinMultiplatformExtension.configureKmmTargets(js: Boolean = true) {
    android {
        publishAllLibraryVariants()
    }
    jvm()
    ios()
    iosArm32()
    iosSimulatorArm64()
    tvos()
    watchos()
    macosX64()
    if (js) {
        js(IR) {
            browser()
        }
    }
}
