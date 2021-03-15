package com.mirego.trikot.foundation

/**
 * Represents a group of four (4) values
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 * Quadruple exhibits value semantics, i.e. two quadruples are equal if all four components are
 * equal.
 *
 * @param A type of the first value.
 * @param B type of the second value.
 * @param C type of the third value.
 * @param D type of the third value.
 * @property first First (1st) value.
 * @property second Second (2nd) value.
 * @property third Third (3rd) value.
 * @property fourth Fourth (4th) value.
 */
data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) {
    /**
     * Returns string representation of the [Quadruple] including its [first], [second], [third] and
     * [fourth] values.
     */
    override fun toString(): String = "($first, $second, $third, $fourth)"
}

/**
 * Represents a group of five (5) values
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 * Quintuple exhibits value semantics, i.e. two quintuples are equal if all five components are
 * equal.
 *
 * @property first First (1st) value.
 * @property second Second (2nd) value.
 * @property third Third (3rd) value.
 * @property fourth Fourth (4th) value.
 * @property fifth Fifth (5th) value.
 */
data class Quintuple<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
) {
    /**
     * Returns string representation of the [Quintuple] including its [first], [second], [third],
     * [fourth] and [fifth] values.
     */
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}

/**
 * Represents a group of six (6) values
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 * Sextuple exhibits value semantics, i.e. two sextuples are equal if all six components are
 * equal.
 *
 * @param A type of the first value.
 * @param B type of the second value.
 * @param C type of the third value.
 * @param D type of the fourth value.
 * @param E type of the fifth value.
 * @param F type of the sixth value.
 * @property first First (1st) value.
 * @property second Second (2nd) value.
 * @property third Third (3rd) value.
 * @property fourth Fourth (4th) value.
 * @property fifth Fifth (5th) value.
 * @property sixth Sixth (6th) value.
 */
data class Sextuple<out A, out B, out C, out D, out E, out F>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F
) {
    /**
     * Returns string representation of the [Sextuple] including its [first], [second], [third],
     * [fourth], [fifth] and [sixth] values.
     */
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth)"
}

/**
 * Represents a group of seven (7) values
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 * Septuple exhibits value semantics, i.e. two septuples are equal if all seven components are
 * equal.
 *
 * @param A type of the first value.
 * @param B type of the second value.
 * @param C type of the third value.
 * @param D type of the fourth value.
 * @param E type of the fifth value.
 * @param F type of the sixth value.
 * @param G type of the seventh value.
 * @property first First (1st) value.
 * @property second Second (2nd) value.
 * @property third Third (3rd) value.
 * @property fourth Fourth (4th) value.
 * @property fifth Fifth (5th) value.
 * @property sixth Sixth (6th) value.
 * @property seventh Seventh (7th) value.
 */
data class Septuple<out A, out B, out C, out D, out E, out F, out G>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G
) {
    /**
     * Returns string representation of the [Septuple] including its [first], [second], [third],
     * [fourth], [fifth], [sixth], and [seventh] values.
     */
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
}

/**
 * Represents a group of eight (8) values
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 * Octuple exhibits value semantics, i.e. two octuples are equal if all eight components are
 * equal.
 *
 * @param A type of the first value.
 * @param B type of the second value.
 * @param C type of the third value.
 * @param D type of the fourth value.
 * @param E type of the fifth value.
 * @param F type of the sixth value.
 * @param G type of the seventh value.
 * @param H type of the eighth value.
 * @property first First (1st) value.
 * @property second Second (2nd) value.
 * @property third Third (3rd) value.
 * @property fourth Fourth (4th) value.
 * @property fifth Fifth (5th) value.
 * @property sixth Sixth (6th) value.
 * @property seventh Seventh (7th) value.
 * @property eighth Eighth (8th) value.
 */
data class Octuple<out A, out B, out C, out D, out E, out F, out G, out H>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H
) {
    /**
     * Returns string representation of the [Octuple] including its [first], [second], [third],
     * [fourth], [fifth], [sixth], [seventh] and [eighth] values.
     */
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, " +
            "$sixth, $seventh, $eighth)"
}

/**
 * Represents a group of nine (9) values
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 * Nonuple exhibits value semantics, i.e. two nonuples are equal if all nine components are
 * equal.
 *
 * @param A type of the first value.
 * @param B type of the second value.
 * @param C type of the third value.
 * @param D type of the fourth value.
 * @param E type of the fifth value.
 * @param F type of the sixth value.
 * @param G type of the seventh value.
 * @param H type of the eighth value.
 * @param I type of the ninth value.
 * @property first First (1st) value.
 * @property second Second (2nd) value.
 * @property third Third (3rd) value.
 * @property fourth Fourth (4th) value.
 * @property fifth Fifth (5th) value.
 * @property sixth Sixth (6th) value.
 * @property seventh Seventh (7th) value.
 * @property eighth Eighth (8th) value.
 * @property ninth Ninth (9th) value.
 */
data class Nonuple<out A, out B, out C, out D, out E, out F, out G, out H, out I>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I
) {
    /**
     * Returns string representation of the [Nonuple] including its [first], [second], [third],
     * [fourth], [fifth], [sixth], [seventh], [eighth] and [ninth] values.
     */
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, " +
            "$sixth, $seventh, $eighth, $ninth)"
}

/**
 * Represents a group of nine (9) values
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 * Decuple exhibits value semantics, i.e. two decuples are equal if all ten components are
 * equal.
 *
 * @param A type of the first value.
 * @param B type of the second value.
 * @param C type of the third value.
 * @param D type of the fourth value.
 * @param E type of the fifth value.
 * @param F type of the sixth value.
 * @param G type of the seventh value.
 * @param H type of the eighth value.
 * @param I type of the ninth value.
 * @param J type of the tenth value.
 * @property first First (1st) value.
 * @property second Second (2nd) value.
 * @property third Third (3rd) value.
 * @property fourth Fourth (4th) value.
 * @property fifth Fifth (5th) value.
 * @property sixth Sixth (6th) value.
 * @property seventh Seventh (7th) value.
 * @property eighth Eighth (8th) value.
 * @property ninth Ninth (9th) value.
 * @property tenth Tenth (10th) value.
 */
data class Decuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J
) {
    /**
     * Returns string representation of the [Nonuple] including its [first], [second], [third],
     * [fourth], [fifth], [sixth], [seventh], [eighth], [ninth] and [tenth] values.
     */
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, " +
            "$sixth, $seventh, $eighth, $ninth, $tenth)"
}
