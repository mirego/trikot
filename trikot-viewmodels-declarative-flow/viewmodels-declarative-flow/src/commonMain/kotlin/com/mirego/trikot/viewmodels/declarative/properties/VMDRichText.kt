package com.mirego.trikot.viewmodels.declarative.properties

data class VMDRichTextSpan(val range: IntRange, val transform: VMDRichTextTransform)

interface VMDRichTextTransform

data class VMDSpanStyleResourceTransform(val textStyleResource: VMDTextStyleResource) : VMDRichTextTransform
