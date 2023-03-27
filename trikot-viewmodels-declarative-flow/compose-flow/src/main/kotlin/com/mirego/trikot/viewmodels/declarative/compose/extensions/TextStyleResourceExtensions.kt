package com.mirego.trikot.viewmodels.declarative.compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.configuration.TrikotViewModelDeclarative
import com.mirego.trikot.viewmodels.declarative.properties.VMDRichTextSpan
import com.mirego.trikot.viewmodels.declarative.properties.VMDSpanStyleResourceTransform
import com.mirego.trikot.viewmodels.declarative.properties.VMDTextStyleResource

fun VMDTextStyleResource.textStyle(): TextStyle? {
    return TrikotViewModelDeclarative.textStyleProvider?.textStyleForResource(this)
}

@Composable
fun VMDTextViewModel.toAnnotatedString() = buildAnnotatedString {
    append(text)
    spans.forEach { SpanStyle(it) }
}

@Composable
private fun AnnotatedString.Builder.SpanStyle(spanStyle: VMDRichTextSpan) {
    spanStyle.toComposeSpanStyle()?.let {
        addStyle(
            it,
            start = spanStyle.range.first,
            end = spanStyle.range.last
        )
    }
}

@Composable
private fun VMDRichTextSpan.toComposeSpanStyle(): SpanStyle? =
    (transform as VMDSpanStyleResourceTransform).textStyleResource.textStyle()?.toSpanStyle()