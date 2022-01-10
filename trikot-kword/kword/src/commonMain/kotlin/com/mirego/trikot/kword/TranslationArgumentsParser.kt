package com.mirego.trikot.kword

internal class TranslationArgumentsParser {
    companion object {
        private val regex = Regex("\\{\\{([^\\}]+)\\}\\}")
    }

    fun replaceInTranslation(translation: String, arguments: Map<String, String>, secondaryArguments: Map<String, String>? = null) =
        translation.replace(regex) {
            arguments[it.groupValues[1]] ?: secondaryArguments?.get(it.groupValues[1]) ?: it.value
        }
}
