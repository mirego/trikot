import com.mirego.trikot.kword.KWord
import com.mirego.trikot.kword.extensions.toKWordKey
import com.mirego.trikot.kword.js.JsKWord
import kotlin.test.Test
import kotlin.test.assertEquals

class JsKWordTest {
    @Test
    fun givenAnExistingKeyThenItReturnsTheTranslation() {
        JsKWord.setCurrentLanguageCode("en")

        assertEquals("a value", KWord["existing_key".toKWordKey])
    }

    @Test
    fun givenALanguageVariantThenItReturnsTheTranslation() {
        JsKWord.setCurrentLanguageCodes(KWord, "en", "variant")

        assertEquals("variant value!", KWord["existing_key".toKWordKey])
    }

    @Test
    fun givenAMissingKeyThenItReturnsTheKeyName() {
        JsKWord.setCurrentLanguageCode("en")

        val kWordKey = "missing_key"

        assertEquals(kWordKey, KWord[kWordKey.toKWordKey])
    }

    @Test
    fun givenALanguageWithoutTranslationFileThenItReturnsTheKeyName() {
        JsKWord.setCurrentLanguageCode("fr")

        val kWordKey = "existing_key"

        assertEquals(kWordKey, KWord[kWordKey.toKWordKey])
    }
}
