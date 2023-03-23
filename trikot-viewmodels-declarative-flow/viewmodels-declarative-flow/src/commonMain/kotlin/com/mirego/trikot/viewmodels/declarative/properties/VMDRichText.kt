package com.mirego.trikot.viewmodels.declarative.properties

data class VMDRichTextSpan(val range: IntRange, val transform: VMDRichTextTransform)

sealed class VMDRichTextTransform

data class VMDSpanStyleResourceTransform(val spanStyleResource: VMDSpanStyleResource) : VMDRichTextTransform()
