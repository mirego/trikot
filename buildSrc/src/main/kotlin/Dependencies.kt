object Versions {
    const val KOTLIN = "1.5.31"
    const val ANDROID_GRADLE_PLUGIN = "7.1.0-rc01"
    const val JETPACK_COMPOSE = "1.1.0-beta03"
    const val COIL = "1.4.0"
    const val KTLINT = "10.2.0"
    const val KOTLINX_SERIALIZATION = "1.3.1"
    const val KTOR = "1.6.4"
    const val KOTLIN_WRAPPERS_EXTENSIONS = "1.0.1-pre.262"
    const val ANDROIDX_LIFECYCLE_EXTENSIONS = "2.2.0"
    const val ANDROIDX_LIFECYCLE = "2.3.0"
}

object Dependencies {
    const val TRIKOT_FOUNDATION = ":trikot-foundation:trikotFoundation"
    const val TRIKOT_STREAMS = ":trikot-streams:streams"
    const val TRIKOT_HTTP = ":trikot-http:http"
    const val TRIKOT_KWORD = ":trikot-kword:kword"
    const val TRIKOT_ANALYTICS = ":trikot-analytics:analytics"
    const val TRIKOT_VIEWMODELS = ":trikot-viewmodels:viewmodels"
    const val TRIKOT_VIEWMODELS_SAMPLE_COMMON = ":trikot-viewmodels:sample:common"
    const val TRIKOT_VIEWMODELS_DECLARATIVE = ":trikot-viewmodels-declarative:viewmodels-declarative"
    const val TRIKOT_VIEWMODELS_DECLARATIVE_COMPOSE = ":trikot-viewmodels-declarative:compose"
    const val TRIKOT_VIEWMODELS_DECLARATIVE_SAMPLE_COMMON = ":trikot-viewmodels-declarative:sample:common"

    const val KTOR_HTTP = "io.ktor:ktor-http:${Versions.KTOR}"
    const val KTOR_LOGGING_JVM = "io.ktor:ktor-client-logging-jvm:${Versions.KTOR}"
    const val KTOR_CLIENT_ANDROID = "io.ktor:ktor-client-android:${Versions.KTOR}"

    const val KOTLINX_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLINX_SERIALIZATION}"
    const val KOTLINX_SERIALIZATION_EXTENSIONS = "org.jetbrains.kotlin-wrappers:kotlin-extensions:${Versions.KOTLIN_WRAPPERS_EXTENSIONS}-kotlin-${Versions.KOTLIN}"
}
