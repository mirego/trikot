package com.mirego.trikot.graphql

import kotlinx.serialization.DeserializationStrategy

interface GraphqlQuery<T> {
    val deserializer: DeserializationStrategy<T>
    val requestBody: String
}

abstract class AbstractGraphqlQuery<T>(override val deserializer: DeserializationStrategy<T>) :
    GraphqlQuery<T> {
    abstract val query: String
    open val variables: Map<String, Any>? = null
    private val escapedQuery: String
        get() {
            return query
                .trimIndent()
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "")
        }

    override val requestBody: String
        get() {
            val stringBuilder = StringBuilder()
            stringBuilder.append("{")
            stringBuilder.append("\"query\": \"$escapedQuery\"")
            variables?.let {
                stringBuilder.append(",\"variables\": {")
                stringBuilder.append(
                    it.map { entry -> "\"${entry.key}\":${entryJson(entry)}" }.joinToString(
                        ","
                    )
                )
                stringBuilder.append("}")
            }
            stringBuilder.append("}")
            return stringBuilder.toString()
        }

    private fun entryJson(entry: Map.Entry<String, Any>): String {
        return anyToJson(entry.value)
    }

    private fun anyToJson(any: Any): String {
        return when (any) {
            is List<*> -> listJson(any)
            is Int -> "$any"
            is GraphqlJsonObject -> any.body
            else -> "\"${any}\""
        }
    }

    private fun listJson(list: List<*>): String {
        return "[${list.filterNotNull().joinToString { anyToJson(it) }}]"
    }
}
