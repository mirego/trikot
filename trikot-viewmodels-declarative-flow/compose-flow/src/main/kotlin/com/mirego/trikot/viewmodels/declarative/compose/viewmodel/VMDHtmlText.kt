package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.text.HtmlCompat
import com.mirego.trikot.viewmodels.declarative.components.VMDHtmlTextViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.vmdModifier
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.internal.html.HtmlText
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.internal.html.linkTextColor

@Composable
fun VMDHtmlText(
    viewModel: VMDHtmlTextViewModel,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    flags: Int = HtmlCompat.FROM_HTML_MODE_COMPACT,
    urlSpanStyle: SpanStyle = SpanStyle(
        color = linkTextColor(),
        textDecoration = TextDecoration.Underline,
    ),
) {
    val textViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    HtmlText(
        text = viewModel.html,
        modifier = modifier.vmdModifier(textViewModel),
        style = style,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        linkClicked = viewModel.urlActionBlock,
        flags = flags,
        urlSpanStyle = urlSpanStyle,
    )
}
