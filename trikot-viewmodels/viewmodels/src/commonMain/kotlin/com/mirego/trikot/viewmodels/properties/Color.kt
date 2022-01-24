package com.mirego.trikot.viewmodels.properties

import com.mirego.trikot.foundation.concurrent.freeze
import kotlin.math.roundToInt

data class Color(val red: Int, val green: Int, val blue: Int, val alpha: Float = 1.0f) {
    private val alphaHex: String
        get() {
            return toHex((alpha * 255).roundToInt())
        }

    fun hex(prefix: String? = null): String {
        return "${prefix ?: ""}${toHex(red)}${toHex(green)}${toHex(blue)}"
    }

    fun hexRGBA(prefix: String?): String {
        return "${prefix ?: ""}${hex()}$alphaHex"
    }

    fun hexARGB(prefix: String?): String {
        return "${prefix ?: ""}$alphaHex${hex()}"
    }

    private fun toHex(int: Int): String {
        val hex = int.toString(16).uppercase()
        return if (hex.length > 1) hex else "0$hex"
    }

    companion object {
        val None = freeze(Color(-1, -1, -1, -1f))
        val Black = freeze(Color(0, 0, 0))
        val White = freeze(Color(255, 255, 255))
    }
}
