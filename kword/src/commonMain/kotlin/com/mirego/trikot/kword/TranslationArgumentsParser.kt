package com.mirego.trikot.kword

class TranslationArgumentsParser {
    fun replaceInTranslation(translation: String, arguments: Array<out Pair<String, String>>): String {
        var parsedTranslation: String = translation

        for (argument in arguments) {
            parsedTranslation = parsedTranslation.replace("{{${argument.first}}}", argument.second)
        }

        return parsedTranslation
    }
}
