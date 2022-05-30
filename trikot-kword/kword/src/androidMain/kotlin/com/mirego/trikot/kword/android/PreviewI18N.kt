package com.mirego.trikot.kword.android

import com.mirego.trikot.kword.DefaultI18N
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * When using the Jetpack Compose previewer in Android Studio, it may be useful to have the actual translations. Since it's
 * running in a different context than the app, the default Android implementation of I18N is not working.
 *
 * This implementation loads a translation file using an absolute path to the translations file in the developer environment.
 *
 * Here is a suggestion on how to setup your project to get this path automatically (without it being mandatory).

 * First, add an entry to the non version-controlled local.properties:
 * ```
 * translation_file_path=/absolute/path/to/translation/file.json
 * ```
 *
 * Then, add this snippet to your build.gradle file (example in Kotlin)
 * ```
 * val properties = gradleLocalProperties(rootDir)
 * val translationFilePath: String = properties.getProperty("translation_file_path", "")
 * buildConfigField("String", "TRANSLATION_FILE_PATH", "\"translationFilePath\"")
 * ```
 *
 * This will add a property in the BuildConfig file. You can use it to instantiate the PreviewI18N class and use it for previews.
 * ```
 * val previewI18N = PreviewI18N(BuildConfig.TRANSLATION_FILE_PATH)
 * ```
 */
class PreviewI18N(filePathAbsolute: String?) : DefaultI18N() {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        allowSpecialFloatingPointValues = true
    }

    init {
        val map = mutableMapOf<String, String>()
        if (filePathAbsolute != null && filePathAbsolute.isNotEmpty()) {
            val fileContent = File(filePathAbsolute).readText()
            map.putAll(json.decodeFromString<Map<String, String>>(fileContent))
            changeLocaleStrings(map)
        }
    }
}
