package com.mirego.trikot.graphql

fun String.escapeForGraphql(): String {
    return this.fold("") { current, value ->
        current + value.escapeForGraphql()
    }
}

fun Char.escapeForGraphql(): String {
    return when (this) {
        0x00.toChar() -> "\\u0000"
        0x01.toChar() -> "\\u0001"
        0x02.toChar() -> "\\u0002"
        0x03.toChar() -> "\\u0003"
        0x04.toChar() -> "\\u0004"
        0x05.toChar() -> "\\u0005"
        0x06.toChar() -> "\\u0006"
        0x07.toChar() -> "\\u0007"
        0x08.toChar() -> "\\u0008"
        0x09.toChar() -> "\\u0009"
        0x0A.toChar() -> "\\u000A"
        0x0B.toChar() -> "\\u000B"
        0x0C.toChar() -> "\\u000C"
        0x0D.toChar() -> "\\u000D"
        0x0E.toChar() -> "\\u000E"
        0x0F.toChar() -> "\\u000F"
        0x10.toChar() -> "\\u0010"
        0x11.toChar() -> "\\u0011"
        0x12.toChar() -> "\\u0012"
        0x13.toChar() -> "\\u0013"
        0x14.toChar() -> "\\u0014"
        0x15.toChar() -> "\\u0015"
        0x16.toChar() -> "\\u0016"
        0x17.toChar() -> "\\u0017"
        0x18.toChar() -> "\\u0018"
        0x19.toChar() -> "\\u0019"
        0x1A.toChar() -> "\\u001A"
        0x1B.toChar() -> "\\u001B"
        0x1C.toChar() -> "\\u001C"
        0x1D.toChar() -> "\\u001D"
        0x1E.toChar() -> "\\u001E"
        0x1F.toChar() -> "\\u001F"
        0x22.toChar() -> "\\u0022"
        0x5C.toChar() -> "\\\\"
        else -> this.toString()
    }
}
