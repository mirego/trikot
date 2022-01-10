import org.gradle.api.Project

val Project.SharedLibs: SharedLibs
    get() = SharedLibs(this)

class SharedLibs(private val project: Project) {
    val Trikot = InnerTrikot()

    inner class InnerTrikot {
        val Foundation = "com.mirego.trikot:trikotFoundation:${project.property("trikot_foundation_version")}"
        val Streams = "com.mirego.trikot:streams:${project.property("trikot_streams_version")}"
        val Viewmodels = "com.mirego.trikot:viewmodels:${project.property("trikot_viewmodels_version")}"
        val Http = "com.mirego.trikot:http:${project.property("trikot_http_version")}"
        val Kword = "com.mirego.trikot:kword:${project.property("trikot_kword_version")}"
    }
}

object Libs {
    object AndroidX {
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.ANDROID_APP_COMPAT}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.ANDROIDX_CONSTRAINT_LAYOUT}"
        const val LifecycleExtensions =
            "androidx.lifecycle:lifecycle-extensions:${Versions.ANDROIDX_LIFECYCLE}"
        const val LifecycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel:${Versions.ANDROIDX_LIFECYCLE}"
        const val LifecycleViewModelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROIDX_LIFECYCLE}"
        const val LifecycleReactiveStreams =
            "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.ANDROIDX_LIFECYCLE}"
        const val Material = "com.google.android.material:material:${Versions.ANDROID_MATERIAL}"
    }

    const val Picasso = "com.squareup.picasso:picasso:${Versions.PICASSO}"

    object Kotlin {
        const val Stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
        const val TestCommon = "org.jetbrains.kotlin:kotlin-test-common"
        const val TestAnnotationCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common"
        const val Test = "org.jetbrains.kotlin:kotlin-test"
        const val TestJUnit = "org.jetbrains.kotlin:kotlin-test-junit"
    }

    object Kotlinx {
        const val SerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLINX_SERIALIZATION}"
    }

    object Mockk {
        const val Common = "io.mockk:mockk-common:${Versions.MOCKK}"
        const val Mockk =  "io.mockk:mockk:${Versions.MOCKK}"
    }
}
