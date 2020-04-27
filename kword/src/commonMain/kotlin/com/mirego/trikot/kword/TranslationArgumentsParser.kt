package com.mirego.trikot.kword

internal class TranslationArgumentsParser {
    private val regex = Regex("\\{\\{([^\\}]+)\\}\\}")

    fun replaceInTranslation(translation: String, arguments: Map<String, String>) =
        translation.replace(regex) {
            arguments[it.groupValues[1]] ?: it.value
        }
}
