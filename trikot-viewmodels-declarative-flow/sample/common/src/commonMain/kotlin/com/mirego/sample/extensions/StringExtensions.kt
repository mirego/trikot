package com.mirego.sample.extensions

fun String.rangeOf(value: String, firstOccurrence: Boolean = true): IntRange {
    val startIndex = if (firstOccurrence) indexOf(value) else lastIndexOf(value)
    val endIndex = startIndex + value.length
    return IntRange(startIndex, endIndex)
}
