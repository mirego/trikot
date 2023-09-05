package com.mirego.sample.ui.theming

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.mirego.vmd.sample.android.R

val AvenirNextFontFamily = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.avenir_next_ultra_light,
            weight = FontWeight.ExtraLight,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.avenir_next_ultra_light_italic,
            weight = FontWeight.ExtraLight,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.avenir_next_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.avenir_next_italic,
            weight = FontWeight.Normal,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.avenir_next_medium,
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.avenir_next_medium_italic,
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.avenir_next_demi_bold,
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.avenir_next_demi_bold_italic,
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.avenir_next_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.avenir_next_bold_italic,
            weight = FontWeight.Bold,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.avenir_next_heavy,
            weight = FontWeight.Black,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.avenir_next_heavy_italic,
            weight = FontWeight.Black,
            style = FontStyle.Italic
        )
    )
)
