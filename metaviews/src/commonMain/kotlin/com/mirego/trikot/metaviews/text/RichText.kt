package com.mirego.trikot.metaviews.text

interface RichText {
    val text: String
    val ranges: List<RichTextRange>
}
