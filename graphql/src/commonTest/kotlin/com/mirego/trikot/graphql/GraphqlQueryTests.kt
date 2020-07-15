package com.mirego.trikot.graphql

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GraphqlQueryTests {

    @Test
    fun `GIVEN query with control characters, they are escaped`() {
        val query = QueryTest()

        assertEquals("{\"query\": \"${expectedString()}\",\"variables\": {\"var\":\"${expectedString()}\"}}", query.requestBody)
    }


    class QueryTest: AbstractGraphqlQuery<String>(String.serializer()) {
        override val variables = mapOf("var" to stringToEscape() + "1234")

        override val query = stringToEscape() + "1234"
    }
}

fun expectedString(): String {
    return stringToEscape().map { " " }.joinToString("") + "1234"
}

fun stringToEscape(): String {
    return (0x00..0x1f).fold("") { current, value ->
        current + value.toChar()
    }
}

