object Versions {
    const val kotlin = "1.5.31"
    const val androidGradlePlugin = "7.1.0-rc01"
    const val jetpackCompose = "1.1.0-beta03"
    const val jetpackComposeToolingPreview = "1.0.5"
    const val coil = "1.4.0"
    const val ktlint = "10.2.0"
    const val kotlinxSerialization = "1.3.1"
    const val ktor = "1.6.4"
    const val kotlinWrappersExtensions = "1.0.1-pre.262"
    const val androidXLifecycle = "2.3.0"
}

object Dependencies {

    const val trikotFoundation = ":trikot-foundation:trikotFoundation"
    const val trikotStreams = ":trikot-streams:streams"
    const val trikotHttp = ":trikot-http:http"
    const val trikotKword = ":trikot-kword:kword"
    const val trikotAnalytics = ":trikot-analytics:analytics"
    const val trikotViewModels = ":trikot-viewmodels:viewmodels"
    const val trikotViewModelsSampleCommon = ":trikot-viewmodels:sample:common"
    const val trikotViewModelsDeclarative = ":trikot-viewmodels-declarative:viewmodels-declarative"

    const val ktorHttp = "io.ktor:ktor-http:${Versions.ktor}"
    const val ktorLoggingJvm = "io.ktor:ktor-client-logging-jvm:${Versions.ktor}"
    const val ktorClientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"

    const val kotlinxSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
    const val kotlinWrapperExtensions = "org.jetbrains.kotlin-wrappers:kotlin-extensions:${Versions.kotlinWrappersExtensions}-kotlin-${Versions.kotlin}"
}
