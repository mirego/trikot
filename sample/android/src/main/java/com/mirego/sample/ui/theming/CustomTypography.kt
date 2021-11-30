package com.mirego.sample.ui.theming

import androidx.compose.material.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
class CustomTypography internal constructor(
    val title1: TextStyle,
    val title1Bold: TextStyle,
    val title2: TextStyle,
    val title2Bold: TextStyle,
    val body: TextStyle,
    val bodyMedium: TextStyle
) {
    constructor(
        defaultFontFamily: FontFamily = FontFamily.Default,
        title1: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp
        ),
        title1Bold: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        ),
        title2: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp
        ),
        title2Bold: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        ),
        body: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        bodyMedium: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    ) : this(
        title1 = title1.withDefaultFontFamily(defaultFontFamily),
        title1Bold = title1Bold.withDefaultFontFamily(defaultFontFamily),
        title2 = title2.withDefaultFontFamily(defaultFontFamily),
        title2Bold = title2Bold.withDefaultFontFamily(defaultFontFamily),
        body = body.withDefaultFontFamily(defaultFontFamily),
        bodyMedium = bodyMedium.withDefaultFontFamily(defaultFontFamily)
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomTypography

        if (title1 != other.title1) return false
        if (title1Bold != other.title1Bold) return false
        if (title2 != other.title2) return false
        if (title2Bold != other.title2Bold) return false
        if (body != other.body) return false
        if (bodyMedium != other.bodyMedium) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title1.hashCode()
        result = 31 * result + title1Bold.hashCode()
        result = 31 * result + title2.hashCode()
        result = 31 * result + title2Bold.hashCode()
        result = 31 * result + body.hashCode()
        result = 31 * result + bodyMedium.hashCode()
        return result
    }

    override fun toString(): String {
        return "CustomTypography(title1=$title1, title1Bold=$title1Bold, title2=$title2, title2Bold=$title2Bold, body=$body, bodyMedium=$bodyMedium)"
    }

    fun copy(
        title1: TextStyle = this.title1,
        title1Bold: TextStyle = this.title1Bold,
        title2: TextStyle = this.title2,
        title2Bold: TextStyle = this.title2Bold,
        body: TextStyle = this.body,
        bodyMedium: TextStyle = this.bodyMedium
    ): CustomTypography = CustomTypography(
        title1 = title1,
        title1Bold = title1Bold,
        title2 = title2,
        title2Bold = title2Bold,
        body = body,
        bodyMedium = bodyMedium
    )

    fun toTypography(): Typography {
        return Typography(
            h1 = title1,
            h2 = title1Bold,
            h3 = title2,
            h4 = title2Bold,
            body1 = body,
            body2 = bodyMedium
        )
    }
}

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = default)
}

internal val LocalCustomTypography = staticCompositionLocalOf { CustomTypography() }
