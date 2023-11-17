package com.mirego.sample.ui.theming

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
class CustomTypography internal constructor(
    val largeTitle: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val headline: TextStyle,
    val body: TextStyle,
    val button: TextStyle,
    val callout: TextStyle,
    val subheadline: TextStyle,
    val footnote: TextStyle,
    val caption1: TextStyle,
    val caption2: TextStyle
) {
    constructor(
        defaultFontFamily: FontFamily = FontFamily.Default,
        largeTitle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 34.sp
        ),
        title1: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp
        ),
        title2: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp
        ),
        title3: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        headline: TextStyle = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp
        ),
        body: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp
        ),
        callout: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        button: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        subheadline: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        ),
        footnote: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp
        ),
        caption1: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        caption2: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp
        )
    ) : this(
        largeTitle = largeTitle.withDefaultFontFamily(defaultFontFamily),
        title1 = title1.withDefaultFontFamily(defaultFontFamily),
        title2 = title2.withDefaultFontFamily(defaultFontFamily),
        title3 = title3.withDefaultFontFamily(defaultFontFamily),
        headline = headline.withDefaultFontFamily(defaultFontFamily),
        body = body.withDefaultFontFamily(defaultFontFamily),
        callout = callout.withDefaultFontFamily(defaultFontFamily),
        button = button.withDefaultFontFamily(defaultFontFamily),
        subheadline = subheadline.withDefaultFontFamily(defaultFontFamily),
        footnote = footnote.withDefaultFontFamily(defaultFontFamily),
        caption1 = caption1.withDefaultFontFamily(defaultFontFamily),
        caption2 = caption2.withDefaultFontFamily(defaultFontFamily)
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomTypography

        if (largeTitle != other.largeTitle) return false
        if (title1 != other.title1) return false
        if (title2 != other.title2) return false
        if (title3 != other.title3) return false
        if (headline != other.headline) return false
        if (body != other.body) return false
        if (callout != other.callout) return false
        if (button != other.button) return false
        if (subheadline != other.subheadline) return false
        if (footnote != other.footnote) return false
        if (caption1 != other.caption1) return false
        if (caption2 != other.caption2) return false

        return true
    }

    override fun hashCode(): Int {
        var result = largeTitle.hashCode()
        result = 31 * result + title1.hashCode()
        result = 31 * result + title2.hashCode()
        result = 31 * result + title3.hashCode()
        result = 31 * result + headline.hashCode()
        result = 31 * result + body.hashCode()
        result = 31 * result + callout.hashCode()
        result = 31 * result + button.hashCode()
        result = 31 * result + subheadline.hashCode()
        result = 31 * result + footnote.hashCode()
        result = 31 * result + caption1.hashCode()
        result = 31 * result + caption2.hashCode()
        return result
    }

    /**
     * Returns a copy of this Typography, optionally overriding some of the values.
     */
    fun copy(
        largeTitle: TextStyle = this.largeTitle,
        title1: TextStyle = this.title1,
        title2: TextStyle = this.title2,
        title3: TextStyle = this.title3,
        headline: TextStyle = this.headline,
        body: TextStyle = this.body,
        callout: TextStyle = this.callout,
        button: TextStyle = this.button,
        subheadline: TextStyle = this.subheadline,
        footnote: TextStyle = this.footnote,
        caption1: TextStyle = this.caption1,
        caption2: TextStyle = this.caption2
    ): CustomTypography = CustomTypography(
        largeTitle = largeTitle,
        title1 = title1,
        title2 = title2,
        title3 = title3,
        headline = headline,
        body = body,
        callout = callout,
        button = button,
        subheadline = subheadline,
        footnote = footnote,
        caption1 = caption1,
        caption2 = caption2
    )

    fun toTypography(): Typography {
        return Typography(
            h1 = largeTitle,
            h2 = title1,
            h3 = title2.bold(),
            h4 = title2,
            h5 = title3.semibold(),
            h6 = title3,
            subtitle1 = headline,
            subtitle2 = headline,
            body1 = body,
            body2 = body.medium(),
            button = button,
            caption = caption1,
            overline = caption2
        )
    }

    override fun toString(): String {
        return "CustomTypography(largeTitle=$largeTitle, title1=$title1, title2=$title2, " +
            "title3=$title3, headline=$headline, body=$body, button=$button, " +
            "callout=$callout, subheadline=$subheadline, footnote=$footnote, " +
            "caption1=$caption1, caption2=$caption2)"
    }
}

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = default)
}

fun TextStyle.weight(fontWeight: FontWeight): TextStyle {
    return copy(fontWeight = fontWeight)
}

fun TextStyle.thin(): TextStyle {
    return copy(fontWeight = FontWeight.Thin)
}

fun TextStyle.extraLight(): TextStyle {
    return copy(fontWeight = FontWeight.ExtraLight)
}

fun TextStyle.light(): TextStyle {
    return copy(fontWeight = FontWeight.Light)
}

fun TextStyle.normal(): TextStyle {
    return copy(fontWeight = FontWeight.Normal)
}

fun TextStyle.medium(): TextStyle {
    return copy(fontWeight = FontWeight.Medium, platformStyle = PlatformTextStyle(includeFontPadding = false))
}

fun TextStyle.semibold(): TextStyle {
    return copy(fontWeight = FontWeight.SemiBold)
}

fun TextStyle.bold(): TextStyle {
    return copy(fontWeight = FontWeight.Bold)
}

fun TextStyle.extraBold(): TextStyle {
    return copy(fontWeight = FontWeight.ExtraBold)
}

fun TextStyle.black(): TextStyle {
    return copy(fontWeight = FontWeight.Black)
}

internal val LocalCustomTypography = staticCompositionLocalOf { CustomTypography() }

val MaterialTheme.customTypography: CustomTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomTypography.current

typealias ComposableType = @Composable (() -> Unit)

@Composable
fun MaterialTheme(
    colors: Colors = MaterialTheme.colors,
    customTypography: CustomTypography = MaterialTheme.customTypography,
    shapes: Shapes = MaterialTheme.shapes,
    content: ComposableType
) {
    MaterialTheme(colors = colors, typography = customTypography.toTypography(), shapes = shapes) {
        CompositionLocalProvider(
            LocalCustomTypography provides customTypography
        ) {
            ProvideTextStyle(value = customTypography.body, content = content)
        }
    }
}
