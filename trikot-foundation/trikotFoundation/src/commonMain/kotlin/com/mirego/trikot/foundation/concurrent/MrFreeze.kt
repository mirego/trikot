package com.mirego.trikot.foundation.concurrent

@Deprecated("No longer needed in new MM")
object MrFreeze {
    @Deprecated(
        "freeze is no longer needed in new MM",
        replaceWith = ReplaceWith("this")
    )
    fun <T> freeze(objectToFreeze: T): T = objectToFreeze

    @Deprecated(
        "ensureNeverFrozen is no longer needed in new MM",
        replaceWith = ReplaceWith("this")
    )
    @Suppress("UNUSED_PARAMETER")
    fun ensureNeverFrozen(objectToProtect: Any) {}
}

@Deprecated(
    "ensureNeverFrozen is no longer needed in new MM",
    replaceWith = ReplaceWith("this")
)
fun <T> freeze(objectToFreeze: T): T = objectToFreeze

