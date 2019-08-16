package com.mirego.trikot.kword.android.ktx

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseMap
import com.mirego.trikot.kword.KWord

object AndroidKWord {
    @UseExperimental(UnstableDefault::class)
    @ImplicitReflectionSerializer
    fun setCurrentLanguageCode(code: String) {
        val fileContent =
            KWord::class.java.getResource("/translations/translation.$code.json")!!
                .readText()
        val json = Json.nonstrict.parseMap<String, String>(fileContent)
        KWord.changeLocaleStrings(json)
    }
}
