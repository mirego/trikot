package com.mirego.trikot.graphql

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.serialization.builtins.serializer

class GraphqlQueryTests {

    @Test
    fun givenQueryWithControlCharactersTheyAreEscaped() {
        val query = QueryTest()

        assertEquals(
            "{\"query\": \"${expectedString()}\",\"variables\": {\"var\":\"${expectedString()}\"}}",
            query.requestBody
        )
    }

    class QueryTest : AbstractGraphqlQuery<String>(String.serializer()) {
        override val variables = mapOf("var" to stringToEscape())

        override val query = stringToEscape()
    }
}

fun expectedString(): String {
    return "\\u0000\\u0001\\u0002\\u0003\\u0004\\u0005\\u0006\\u0007\\u0008\\u0009\\u000A\\u000B\\u000C\\u000D\\u000E\\u000F\\u0010\\u0011\\u0012\\u0013\\u0014\\u0015\\u0016\\u0017\\u0018\\u0019\\u001A\\u001B\\u001C\\u001D\\u001E\\u001F" + "\\u0022\\u000D\\\\1234"
}

fun stringToEscape(): String {
    return (0x00..0x1f).fold("") { current, value ->
        current + value.toChar()
    } + "\"\r\\1234"
}
