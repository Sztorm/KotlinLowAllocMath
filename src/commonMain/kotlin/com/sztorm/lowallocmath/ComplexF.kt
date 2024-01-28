@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.Vector2F.Companion.ZERO
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.math.*

/** Returns a complex number with the specified imaginary value. **/
inline val Float.i
    get() = ComplexF(0f, this)

/** Converts this [Float] value to [ComplexF]. **/
inline fun Float.toComplexF() = ComplexF(this, 0f)

/** Adds the [other] complex number to this real number. **/
inline operator fun Float.plus(other: ComplexF) =
    ComplexF(this + other.real, other.imaginary)

/** Subtracts the [other] complex number from this real number. **/
inline operator fun Float.minus(other: ComplexF) =
    ComplexF(this - other.real, -other.imaginary)

/** Multiplies this real number by the [other] complex number. **/
inline operator fun Float.times(other: ComplexF) =
    ComplexF(this * other.real, this * other.imaginary)

/** Divides this real number by the [other] complex number. **/
operator fun Float.div(other: ComplexF): ComplexF {
    val r1 = other.real
    val i1 = other.imaginary

    return if (i1.absoluteValue < r1.absoluteValue) {
        val i1OverR1 = i1 / r1
        val divisor = r1 + i1 * i1OverR1

        ComplexF(this / divisor, (-this * i1OverR1) / divisor)
    } else {
        val r1OverI1 = r1 / i1
        val divisor = i1 + r1 * r1OverI1

        ComplexF((this * r1OverI1) / divisor, -this / divisor)
    }
}

/**
 * Represents a complex number composed of two single-precision 32-bit IEEE 754 floating point
 * numbers for real and imaginary parts.
 *
 * On the JVM, non-nullable values of this type are represented as values of the primitive type
 * `long`.
 */
@JvmInline
value class ComplexF internal constructor(internal val data: Long) {
    /** Constructs a new complex number using the specified [real] and [imaginary] parts. **/
    constructor(real: Float, imaginary: Float) : this(
        (real.toRawBits().toLong() and 0xFFFFFFFFL) or
                (imaginary.toRawBits().toLong() shl Float.SIZE_BITS)
    )

    /** Real part of the complex number. **/
    val real: Float
        get() = Float.fromBits(data.toInt())

    /** Imaginary part of the complex number. **/
    val imaginary: Float
        get() = Float.fromBits((data ushr Float.SIZE_BITS).toInt())

    /** Returns the squared magnitude of this complex number. **/
    inline val squaredMagnitude: Float
        get() {
            val r = this.real
            val i = this.imaginary
            return r * r + i * i
        }

    /**
     * Returns the magnitude of this complex number.
     *
     * This property is equal to the [absoluteValue].
     */
    inline val magnitude: Float
        get() = sqrt(squaredMagnitude)

    /**
     * Returns the absolute value of this complex number.
     *
     * This property is equal to the [magnitude].
     */
    inline val absoluteValue: Float
        get() = magnitude

    /** Returns the phase of this complex number. **/
    inline val phase: Float
        get() = atan2(imaginary, real)

    /** Returns the phase of this complex number represented as angle. **/
    inline val phaseAngle: AngleF
        get() = AngleF(atan2(imaginary, real))

    /** Returns the conjugate of this complex number. **/
    inline val conjugate
        get() = ComplexF(real, -imaginary)

    /**
     * Returns a normalized copy of this complex number if this complex number [magnitude] is large
     * enough to safely normalize. Else returns [ZERO].
     */
    inline val normalized: ComplexF
        get() {
            val magnitude: Float = this.magnitude

            return if (magnitude > 0.00001f) this / magnitude
            else ZERO
        }

    /**
     * Returns a normalized copy of this complex number if this complex number [magnitude] is large
     * enough to safely normalize. Else returns [defaultValue].
     *
     * @param [defaultValue] The returned value if this complex number could not be safely
     * normalized.
     */
    inline fun normalizedOrElse(defaultValue: ComplexF): ComplexF {
        val magnitude: Float = this.magnitude

        return if (magnitude > 0.00001f) this / magnitude
        else defaultValue
    }

    /** Converts this [ComplexF] value to [Vector2F]. **/
    fun toVector2F() = Vector2F(data)

    /** Returns a [String] representation of this vector in "[real] ± [imaginary]i" format. **/
    override fun toString(): String = (when {
        imaginary == 0f -> StringBuilder(16)
            .append(real)

        imaginary < 0f -> StringBuilder(16 + 3 + 16 + 1)
            .append(real).append(" - ").append(imaginary.absoluteValue).append('i')

        else -> StringBuilder(16 + 3 + 16 + 1)
            .append(real).append(" + ").append(imaginary).append('i')
    }).toString()

    /**
     * Returns a value indicating whether this number is approximately [other] number given
     * specified [epsilon] tolerance.
     */
    inline fun isApproximately(other: ComplexF, epsilon: Float = 0.00001f): Boolean =
        real.isApproximately(other.real, epsilon) and
                imaginary.isApproximately(other.imaginary, epsilon)

    /** Returns the dot product of this and [other] complex number. **/
    inline infix fun dot(other: ComplexF): Float = real * other.real + imaginary * other.imaginary

    /** Returns this complex number to the power of [x]. **/
    fun pow(x: ComplexF): ComplexF {
        if (x == ZERO) {
            return ONE
        }
        if (this == ZERO) {
            return ZERO
        }
        val r: Float = real
        val i: Float = imaginary
        val xR: Float = x.real
        val xI: Float = x.imaginary
        val mag: Float = absoluteValue
        val phase: Float = atan2(i, r)
        val rho: Float = xR * phase + xI * ln(mag)
        val t = mag.pow(xR) * E.toFloat().pow(-xI * phase)

        return ComplexF(t * cos(rho), t * sin(rho))
    }

    /** Returns this complex number to the power of [x]. **/
    fun pow(x: Float): ComplexF {
        if (x == 0f) {
            return ONE
        }
        if (this == ZERO) {
            return ZERO
        }
        val r: Float = real
        val i: Float = imaginary
        val mag: Float = sqrt(r * r + i * i)
        val phase: Float = atan2(i, r)
        val rho: Float = x * phase
        val t = mag.pow(x)

        return ComplexF(t * cos(rho), t * sin(rho))
    }

    /** Returns this complex number to the power of [n]. **/
    fun pow(n: Int): ComplexF {
        if (n == 0) {
            return ONE
        }
        if (this == ZERO) {
            return ZERO
        }
        val r: Float = real
        val i: Float = imaginary
        val mag: Float = absoluteValue
        val phase: Float = atan2(i, r)
        val rho: Float = n * phase
        val t = mag.pow(n)

        return ComplexF(t * cos(rho), t * sin(rho))
    }

    /** Returns a copy of this instance with specified properties altered. **/
    inline fun copy(real: Float = this.real, imaginary: Float = this.imaginary) =
        ComplexF(real, imaginary)

    /** Real part of the complex number. **/
    inline operator fun component1(): Float = real

    /** Imaginary part of the complex number. **/
    inline operator fun component2(): Float = imaginary

    /** Returns this complex number. **/
    inline operator fun unaryPlus() = this

    /** Returns the additive inverse of this complex number. **/
    inline operator fun unaryMinus() = ComplexF(-real, -imaginary)

    /** Adds the [other] complex number to this complex number. **/
    inline operator fun plus(other: ComplexF) =
        ComplexF(real + other.real, imaginary + other.imaginary)

    /** Adds the [other] real number to this complex number. **/
    inline operator fun plus(other: Float) = ComplexF(real + other, imaginary)

    /** Subtracts the [other] complex number from this complex number. **/
    inline operator fun minus(other: ComplexF) =
        ComplexF(real - other.real, imaginary - other.imaginary)

    /** Subtracts the [other] real number from this complex number. **/
    inline operator fun minus(other: Float) = ComplexF(real - other, imaginary)

    /** Multiplies this complex number by the [other] complex number. **/
    inline operator fun times(other: ComplexF): ComplexF {
        val r0 = real
        val i0 = imaginary
        val r1 = other.real
        val i1 = other.imaginary

        return ComplexF(r0 * r1 - i0 * i1, i0 * r1 + r0 * i1)
    }

    /** Multiplies this complex number by the [other] real number. **/
    inline operator fun times(other: Float) = ComplexF(real * other, imaginary * other)

    /** Divides this complex number by the [other] complex number. **/
    operator fun div(other: ComplexF): ComplexF {
        val r0 = real
        val i0 = imaginary
        val r1 = other.real
        val i1 = other.imaginary

        return if (i1.absoluteValue < r1.absoluteValue) {
            val i1OverR1 = i1 / r1
            val divisor = r1 + i1 * i1OverR1

            ComplexF((r0 + i0 * i1OverR1) / divisor, (i0 - r0 * i1OverR1) / divisor)
        } else {
            val r1OverI1 = r1 / i1
            val divisor = i1 + r1 * r1OverI1

            ComplexF((i0 + r0 * r1OverI1) / divisor, (i0 * r1OverI1 - r0) / divisor)
        }
    }

    /** Divides this complex number by the [other] real number. **/
    inline operator fun div(other: Float) = ComplexF(real / other, imaginary / other)

    companion object {
        private const val LN10_INVERTED = 0.4342945f

        /** The number of bits used to represent an instance of [ComplexF] in a binary form. **/
        const val SIZE_BITS: Int = 64

        /** The number of bytes used to represent an instance of [ComplexF] in a binary form. **/
        const val SIZE_BYTES: Int = 8

        /** Value of 0 **/
        inline val ZERO
            @JvmStatic get() = ComplexF(0f, 0f)

        /** Value of 1 **/
        inline val ONE
            @JvmStatic get() = ComplexF(1f, 0f)

        /** Value of i **/
        inline val IMAGINARY_ONE
            @JvmStatic get() = ComplexF(0f, 1f)

        /**
         *  Creates a new complex number using the specified polar coordinates.
         *
         *  @param magnitude Distance from the origin to the number.
         *  @param phase Angle measured in radians.
         */
        @JvmStatic
        inline fun fromPolar(magnitude: Float, phase: Float) =
            ComplexF(magnitude * cos(phase), magnitude * sin(phase))

        /** Creates a new complex number using the specified [angle]. **/
        @JvmStatic
        inline fun fromAngle(angle: AngleF) = ComplexF(cos(angle.radians), sin(angle.radians))

        /** Returns the absolute value of the specified complex number. **/
        @JvmStatic
        inline fun abs(value: ComplexF) = value.absoluteValue

        /** Returns the conjugate of the specified complex number. **/
        @JvmStatic
        inline fun conjugate(value: ComplexF) = ComplexF(value.real, -value.imaginary)

        /** Returns the [E] constant to the power of specified complex number. **/
        @JvmStatic
        fun exp(value: ComplexF): ComplexF {
            val rExp: Float = exp(value.real)
            val i: Float = value.imaginary

            return ComplexF(rExp * cos(i), rExp * sin(i))
        }

        /** Returns the natural logarithm (base `E`) of the specified complex number. **/
        @JvmStatic
        fun ln(value: ComplexF) =
            ComplexF(ln(value.absoluteValue), atan2(value.imaginary, value.real))

        /** Returns the logarithm of the specified complex number to the given [base]. **/
        @JvmStatic
        fun log(value: ComplexF, base: Float) = ln(value) / ln(base)

        /** Returns the common logarithm (base 10) of the specified complex number. **/
        @JvmStatic
        fun log10(value: ComplexF) = ComplexF(
            ln(value.absoluteValue) * LN10_INVERTED,
            atan2(value.imaginary, value.real) * LN10_INVERTED
        )

        /** Returns the cosine of the specified complex number. **/
        @JvmStatic
        fun cos(value: ComplexF): ComplexF {
            val r: Float = value.real
            val iExp: Float = exp(value.imaginary)
            val iExpReciprocal: Float = 1f / iExp
            val sinh: Float = (iExp - iExpReciprocal) * 0.5f
            val cosh: Float = (iExp + iExpReciprocal) * 0.5f

            return ComplexF(cos(r) * cosh, -sin(r) * sinh)
        }

        /** Returns the sine of the specified complex number. **/
        @JvmStatic
        fun sin(value: ComplexF): ComplexF {
            val r: Float = value.real
            val iExp: Float = exp(value.imaginary)
            val iExpReciprocal: Float = 1f / iExp
            val sinh: Float = (iExp - iExpReciprocal) * 0.5f
            val cosh: Float = (iExp + iExpReciprocal) * 0.5f

            return ComplexF(sin(r) * cosh, cos(r) * sinh)
        }

        /** Returns the tangent of the specified complex number. **/
        @JvmStatic
        fun tan(value: ComplexF): ComplexF {
            val r: Float = value.real
            val iExp: Float = exp(value.imaginary)
            val iExpReciprocal: Float = 1f / iExp
            val sinh: Float = (iExp - iExpReciprocal) * 0.5f
            val cosh: Float = (iExp + iExpReciprocal) * 0.5f
            val cosR: Float = cos(r)
            val sinR: Float = sin(r)
            val sin = ComplexF(sinR * cosh, cosR * sinh)
            val cos = ComplexF(cosR * cosh, -sinR * sinh)

            return sin / cos
        }

        /** Returns the hyperbolic cosine of the specified complex number. **/
        @JvmStatic
        inline fun cosh(value: ComplexF): ComplexF = cos(ComplexF(-value.imaginary, value.real))

        /** Returns the hyperbolic sine of the specified complex number. **/
        @JvmStatic
        inline fun sinh(value: ComplexF): ComplexF {
            val sin: ComplexF = sin(ComplexF(-value.imaginary, value.real))
            return ComplexF(sin.imaginary, -sin.real)
        }

        /** Returns the hyperbolic tangent of the specified complex number. **/
        @JvmStatic
        inline fun tanh(value: ComplexF): ComplexF {
            val tan: ComplexF = tan(ComplexF(-value.imaginary, value.real))
            return ComplexF(tan.imaginary, -tan.real)
        }

        /** Returns the square root of the specified complex number. **/
        @JvmStatic
        fun sqrt(value: ComplexF): ComplexF {
            if (value == ZERO) {
                return ZERO
            }
            val r: Float = value.real
            val i: Float = value.imaginary

            if (i == 0f) {
                return ComplexF(sqrt(r), 0f)
            }
            val t: Float = sqrt((r.absoluteValue + value.absoluteValue) / 2)

            return if (r >= 0) {
                ComplexF(t, i / (2 * t))
            } else {
                ComplexF(i.absoluteValue / (2 * t), 1f.withSign(i) * t)
            }
        }

        /**
         * Returns a normalized linear interpolation of two rotations [a] and [b] with interpolator
         * value of [t].
         *
         * The [a] and [b] arguments are expected to be normalized.
         * The [t] argument is expected to be in range of 0..1.
         *
         * Given the constant velocity of [t], the linear velocity of interpolation is
         * constant, but the angular velocity is not.
         *
         * This function can be used as an approximation of [slerp] when angle between [a] and [b]
         * is small.
         */
        @JvmStatic
        @Suppress("SpellCheckingInspection")
        fun nlerp(a: ComplexF, b: ComplexF, t: Float): ComplexF {
            val (aR: Float, aI: Float) = a
            val (bR: Float, bI: Float) = b
            val cR: Float = Float.lerp(aR, bR, t)
            val cI: Float = Float.lerp(aI, bI, t)
            val magnitude: Float = sqrt(cR * cR + cI * cI)
            println(magnitude)
            return if (magnitude > 0.00001f) ComplexF(cR / magnitude, cI / magnitude)
            else if (t < 0.5f) a else b
        }

        /**
         * Returns a spherical linear interpolation of two rotations [a] and [b] with interpolator
         * value of [t].
         *
         * The [a] and [b] arguments are expected to be normalized.
         * The function does not clamp the [t] parameter, however the most accurate results are
         * returned when [t] is in range of 0..1.
         *
         * The function always returns the shortest interpolation path between [a] and [b].
         *
         * The angular velocity of interpolation is constant, given constant velocity of [t].
         */
        @JvmStatic
        @Suppress("SpellCheckingInspection")
        fun slerp(a: ComplexF, b: ComplexF, t: Float): ComplexF {
            val (aR: Float, aI: Float) = a
            val (bR: Float, bI: Float) = b
            val cR: Float = aR * bR + aI * bI
            val cI: Float = aR * bI - aI * bR
            val mag: Float = sqrt(cR * cR + cI * cI)
            val phase: Float = atan2(cI, cR)
            val rho: Float = t * phase
            val p: Float = mag.pow(t)
            val dR: Float = p * cos(rho)
            val dI: Float = p * sin(rho)

            return ComplexF(aR * dR - aI * dI, aI * dR + aR * dI)
        }
    }
}