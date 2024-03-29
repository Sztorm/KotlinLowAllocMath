@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.sztorm.lowallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

/**
 * Represents a 32-bit representation of RGBA color.
 *
 * On the JVM, non-nullable values of this type are represented as values of the primitive type
 * `int`.
 */
@JvmInline
value class Color32 private constructor(private val data: Int) {

    /** Constructs a new [Color32] instance with given [r], [g], [b], [a] components. **/
    constructor(r: UByte, g: UByte, b: UByte, a: UByte) : this(
        data =
        r.toInt() + (g.toInt() shl 8) + (b.toInt() shl 16) + (a.toInt() shl 24)
    )

    /** Red component of the color. **/
    val r: UByte
        get() = data.toUByte()

    /** Green component of the color. **/
    val g: UByte
        get() = (data ushr 8).toUByte()

    /** Blue component of the color. **/
    val b: UByte
        get() = (data ushr 16).toUByte()

    /** Alpha component of the color. **/
    val a: UByte
        get() = (data ushr 24).toUByte()

    /**
     * Returns a [String] representation of this color in "Color32(r=[r], g=[g], b=[b], a=[a])"
     * format.
     */
    override fun toString(): String = StringBuilder(10 + 3 + 4 + 3 + 4 + 3 + 4 + 3 + 1)
        .append("Color32(r=").append(r)
        .append(", g=").append(g)
        .append(", b=").append(b)
        .append(", a=").append(a)
        .append(')')
        .toString()

    /** Red component of the color. **/
    inline operator fun component1(): UByte = r

    /** Green component of the color. **/
    inline operator fun component2(): UByte = g

    /** Blue component of the color. **/
    inline operator fun component3(): UByte = b

    /** Alpha component of the color. **/
    inline operator fun component4(): UByte = a

    /**
     * Returns a component specified by an [index].
     *
     * @throws [IndexOutOfBoundsException] when [index] is out of range of 0..3.
     */
    operator fun get(index: Int): UByte =
        if (index.toUInt() < 4u) (data ushr (index shl 3)).toUByte()
        else throw IndexOutOfBoundsException("Index of $index is out of range of 0..3")

    companion object {

        /** The number of bits used to represent an instance of [Color32] in a binary form. **/
        const val SIZE_BITS: Int = 32

        /** The number of bytes used to represent an instance of [Color32] in a binary form. **/
        const val SIZE_BYTES: Int = 4

        /** Value of (0, 0, 0, 255) that represents the black color. **/
        inline val BLACK
            @JvmStatic get() = Color32(0u, 0u, 0u, 255u)

        /** Value of (255, 255, 255, 255) that represents the white color. **/
        inline val WHITE
            @JvmStatic get() = Color32(255u, 255u, 255u, 255u)

        /** Value of (255, 0, 0, 255) that represents the red color. **/
        inline val RED
            @JvmStatic get() = Color32(255u, 0u, 0u, 255u)

        /** Value of (0, 255, 0, 255) that represents the green color. **/
        inline val GREEN
            @JvmStatic get() = Color32(0u, 255u, 0u, 255u)

        /** Value of (0, 0, 255, 255) that represents the blue color. **/
        inline val BLUE
            @JvmStatic get() = Color32(0u, 0u, 255u, 255u)

        /** Value of (255, 255, 0, 255) that represents the yellow color. **/
        inline val YELLOW
            @JvmStatic get() = Color32(255u, 255u, 0u, 255u)

        /** Value of (255, 0, 255, 255) that represents the magenta color. **/
        inline val MAGENTA
            @JvmStatic get() = Color32(255u, 0u, 255u, 255u)

        /** Value of (0, 255, 255, 255) that represents the cyan color. **/
        inline val CYAN
            @JvmStatic get() = Color32(0u, 255u, 255u, 255u)

        /**
         * Returns a linearly interpolated color between [a] and [b] colors.
         *
         * @param [a] Source color.
         * @param [b] Destination color.
         * @param [t] Interpolator value with an expected range of 0..1.
         */
        @JvmStatic
        @Suppress("SpellCheckingInspection")
        fun lerp(a: Color32, b: Color32, t: Float): Color32 {
            val ar: UByte = a.r
            val ag: UByte = a.g
            val ab: UByte = a.b
            val aa: UByte = a.a
            val bb: UByte = b.b

            return Color32(
                (ar.toFloat() + (b.r.toInt() - ar.toInt()).toFloat() * t).toUInt().toUByte(),
                (ag.toFloat() + (b.g.toInt() - ag.toInt()).toFloat() * t).toUInt().toUByte(),
                (ab.toFloat() + (bb.toInt() - ab.toInt()).toFloat() * t).toUInt().toUByte(),
                (aa.toFloat() + (b.a.toInt() - aa.toInt()).toFloat() * t).toUInt().toUByte()
            )
        }
    }
}