/*
 * Copyright 2023 compose-html
 * https://github.com/viluahealthcare/compose-html
 *
 * Modified for Trikot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mirego.trikot.viewmodels.declarative.compose.viewmodel.internal.html

import android.graphics.Typeface
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.TypefaceSpan
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import java.io.File

private const val PATH_SYSTEM_FONTS_FILE = "/system/etc/fonts.xml"
private const val PATH_SYSTEM_FONTS_DIR = "/system/fonts/"

internal fun RelativeSizeSpan.spanStyle(fontSize: TextUnit): SpanStyle =
    SpanStyle(fontSize = (fontSize.value * sizeChange).sp)

internal fun StyleSpan.spanStyle(): SpanStyle? = when (style) {
    Typeface.BOLD -> SpanStyle(fontWeight = FontWeight.Bold)
    Typeface.ITALIC -> SpanStyle(fontStyle = FontStyle.Italic)
    Typeface.BOLD_ITALIC -> SpanStyle(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
    )
    else -> null
}

internal fun SubscriptSpan.spanStyle(): SpanStyle =
    SpanStyle(baselineShift = BaselineShift.Subscript)

internal fun SuperscriptSpan.spanStyle(): SpanStyle =
    SpanStyle(baselineShift = BaselineShift.Superscript)

internal fun TypefaceSpan.spanStyle(): SpanStyle? {
    val xmlContent = File(PATH_SYSTEM_FONTS_FILE).readText()
    return if (xmlContent.contains("""<family name="$family""")) {
        val familyChunkXml = xmlContent.substringAfter("""<family name="$family""")
            .substringBefore("""</family>""")
        val fontName = familyChunkXml.substringAfter("""<font weight="400" style="normal">""")
            .substringBefore("</font>")
        SpanStyle(fontFamily = FontFamily(Typeface.createFromFile("$PATH_SYSTEM_FONTS_DIR$fontName")))
    } else {
        null
    }
}
