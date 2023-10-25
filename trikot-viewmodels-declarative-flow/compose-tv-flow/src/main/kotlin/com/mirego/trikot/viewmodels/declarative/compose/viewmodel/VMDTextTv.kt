package com.mirego.trikot.viewmodels.declarative.compose.viewmodel


import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.LocalTextStyle
import androidx.tv.material3.Text
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.isOverridingAlpha
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.extensions.toAnnotatedString
import com.mirego.trikot.viewmodels.declarative.compose.extensions.vmdModifier

@ExperimentalTvMaterial3Api
@Composable
fun VMDTextTv(
    modifier: Modifier = Modifier,
    viewModel: VMDTextViewModel,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val textViewModel by viewModel.observeAsState(excludedProperties = if (modifier.isOverridingAlpha()) listOf(viewModel::isHidden) else emptyList())

    Text(
        text = textViewModel.toAnnotatedString(),
        modifier = modifier.vmdModifier(textViewModel),
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        inlineContent = inlineContent,
        style = style
    )
}
