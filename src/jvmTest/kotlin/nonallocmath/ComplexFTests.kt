package nonallocmath

import com.sztorm.nonallocmath.*
import nonallocmath.utils.Wrapper
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class ComplexFTests {

    @ParameterizedTest
    @MethodSource("complices")
    fun basicPropertiesAreValid(wrappedValue: Wrapper<ComplexF>) {
        val value: ComplexF = wrappedValue.value
        val (real, imaginary) = value

        assertEquals(value.real.toRawBits(), real.toRawBits())
        assertEquals(value.imaginary.toRawBits(), imaginary.toRawBits())
    }

    @ParameterizedTest
    @MethodSource("squaredMagnitudeArgs")
    fun squaredMagnitudeReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Float) =
        assertTrue(expected.isApproximately(wrappedValue.value.squaredMagnitude, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("magnitudeArgs")
    fun magnitudeReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Float) =
        assertTrue(expected.isApproximately(wrappedValue.value.magnitude, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("magnitudeArgs")
    fun absoluteValueReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Float) =
        assertTrue(expected.isApproximately(wrappedValue.value.absoluteValue, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("phaseArgs")
    fun phaseReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Float) =
        assertTrue(expected.isApproximately(wrappedValue.value.phase, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("powComplexArgs")
    fun powComplexReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a.value.pow(b.value), epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("powFloatArgs")
    fun powFloatReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Float, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a.value.pow(b), epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("powIntArgs")
    fun powIntReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Int, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a.value.pow(b), epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("fromPolarArgs")
    fun fromPolarReturnsCorrectValue(magnitude: Float, phase: Float, expected: Wrapper<ComplexF>) =
        assertTrue(
            expected.value.isApproximately(ComplexF.fromPolar(magnitude, phase), epsilon = 0.001f)
        )

    @ParameterizedTest
    @MethodSource("magnitudeArgs")
    fun absReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Float) =
        assertTrue(expected.isApproximately(ComplexF.abs(wrappedValue.value), epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("conjugateArgs")
    fun conjugateReturnsCorrectValue(
        wrappedValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(
        expected.value.isApproximately(ComplexF.conjugate(wrappedValue.value), epsilon = 0.001f)
    )

    @ParameterizedTest
    @MethodSource("expArgs")
    fun expReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertTrue(
            expected.value.isApproximately(ComplexF.exp(wrappedValue.value), epsilon = 0.001f)
        )

    @ParameterizedTest
    @MethodSource("cosArgs")
    fun cosReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertTrue(
            expected.value.isApproximately(ComplexF.cos(wrappedValue.value), epsilon = 0.001f)
        )

    @ParameterizedTest
    @MethodSource("sinArgs")
    fun sinReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertTrue(
            expected.value.isApproximately(ComplexF.sin(wrappedValue.value), epsilon = 0.001f)
        )

    @ParameterizedTest
    @MethodSource("tanArgs")
    fun tanReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertTrue(
            expected.value.isApproximately(ComplexF.tan(wrappedValue.value), epsilon = 0.001f)
        )

    @ParameterizedTest
    @MethodSource("coshArgs")
    fun coshReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertTrue(
            expected.value.isApproximately(ComplexF.cosh(wrappedValue.value), epsilon = 0.001f)
        )

    @ParameterizedTest
    @MethodSource("sinhArgs")
    fun sinhReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertTrue(
            expected.value.isApproximately(ComplexF.sinh(wrappedValue.value), epsilon = 0.001f)
        )

    @ParameterizedTest
    @MethodSource("tanhArgs")
    fun tanhReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertTrue(
            expected.value.isApproximately(ComplexF.tanh(wrappedValue.value), epsilon = 0.001f)
        )

    @ParameterizedTest
    @MethodSource("sqrtArgs")
    fun sqrtReturnsCorrectValue(wrappedValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertTrue(
            expected.value.isApproximately(ComplexF.sqrt(wrappedValue.value), epsilon = 0.001f)
        )

    @ParameterizedTest
    @MethodSource("plusComplexArgs")
    fun unaryPlusOperatorReturnsCorrectValue(
        wrappedValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(equalsBitwise(expected.value, +wrappedValue.value))

    @ParameterizedTest
    @MethodSource("minusComplexArgs")
    fun unaryMinusOperatorReturnsCorrectValue(
        wrappedValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(equalsBitwise(expected.value, -wrappedValue.value))

    @ParameterizedTest
    @MethodSource("complexPlusComplexArgs")
    fun complexPlusComplexReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a.value + b.value, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("complexPlusFloatArgs")
    fun complexPlusFloatReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Float, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a.value + b, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("floatPlusComplexArgs")
    fun floatPlusComplexReturnsCorrectValue(
        a: Float, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a + b.value, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("complexMinusComplexArgs")
    fun complexMinusComplexReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a.value - b.value, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("complexMinusFloatArgs")
    fun complexMinusFloatReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Float, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a.value - b, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("floatMinusComplexArgs")
    fun floatMinusComplexReturnsCorrectValue(
        a: Float, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a - b.value, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("complexTimesComplexArgs")
    fun complexTimesComplexReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a.value * b.value, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("complexTimesFloatArgs")
    fun complexTimesFloatReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Float, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a.value * b, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("floatTimesComplexArgs")
    fun floatTimesComplexReturnsCorrectValue(
        a: Float, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a * b.value, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("complexDivComplexArgs")
    fun complexDivComplexReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a.value / b.value, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("complexDivFloatArgs")
    fun complexDivFloatReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Float, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a.value / b, epsilon = 0.001f))

    @ParameterizedTest
    @MethodSource("floatDivComplexArgs")
    fun floatDivComplexReturnsCorrectValue(
        a: Float, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(expected.value.isApproximately(a / b.value, epsilon = 0.001f))

    companion object {
        /** Compares complices bitwise. Useful when comparing NaNs. **/
        @JvmStatic
        fun equalsBitwise(a: ComplexF, b: ComplexF) =
            a.real.toRawBits() == b.real.toRawBits() &&
                    a.imaginary.toRawBits() == b.imaginary.toRawBits()

        @JvmStatic
        fun complices(): List<Arguments> = listOf(
            Arguments.of(Wrapper(ComplexF(2f, 4f))),
            Arguments.of(Wrapper(ComplexF(2f, Float.NaN))),
            Arguments.of(Wrapper(ComplexF(Float.POSITIVE_INFINITY, -1f))),
            Arguments.of(Wrapper(ComplexF(-1f, Float.NEGATIVE_INFINITY))),
        )

        @JvmStatic
        fun squaredMagnitudeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(ComplexF(3f, 4f)), 25f),
            Arguments.of(Wrapper(ComplexF(3f, -4f)), 25f),
        )

        @JvmStatic
        fun magnitudeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(ComplexF(3f, 4f)), 5f),
            Arguments.of(Wrapper(ComplexF(3f, -4f)), 5f),
        )

        @JvmStatic
        fun phaseArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(ComplexF(3f, 4f)), 0.9273f),
            Arguments.of(Wrapper(ComplexF(0f, 2f)), 1.5708f),
            Arguments.of(Wrapper(ComplexF(0f, 0f)), 0f),
        )

        @JvmStatic
        fun powComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(-2f, 3f)),
                Wrapper(ComplexF(-1.3f, 0.5f)),
                Wrapper(ComplexF(-0.03592f, -0.05314f))
            ),
            Arguments.of(
                Wrapper(ComplexF(7f, -1f)),
                Wrapper(ComplexF(-0.1f, 0f)),
                Wrapper(ComplexF(0.82226f, 0.01167f))
            ),
            Arguments.of(
                Wrapper(ComplexF(-7f, -1f)),
                Wrapper(ComplexF(0f, 2f)),
                Wrapper(ComplexF(-289.329f, -280.795f))
            ),
        )

        @JvmStatic
        fun powFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0f)), 0f,
                Wrapper(ComplexF(1f, 0f))
            ),
            Arguments.of(
                Wrapper(ComplexF(3f, -1f)), -0.1f,
                Wrapper(ComplexF(0.89079f, 0.02867f))
            ),
        )

        @JvmStatic
        fun powIntArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(-2f, 3f)), 2,
                Wrapper(ComplexF(-5f, -12f))
            ),
            Arguments.of(
                Wrapper(ComplexF(7f, -1f)), 0,
                Wrapper(ComplexF(1f, 0f))
            ),
        )

        @JvmStatic
        fun fromPolarArgs(): List<Arguments> = listOf(
            Arguments.of(
                5f, 0.9273f,
                Wrapper(ComplexF(3f, 4f))
            ),
            Arguments.of(
                5f, -0.9273f,
                Wrapper(ComplexF(3f, -4f))
            ),
        )

        @JvmStatic
        fun conjugateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)),
                Wrapper(ComplexF(3f, -4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(3f, -4f)),
                Wrapper(ComplexF(3f, 4f))
            ),
        )

        @JvmStatic
        fun expArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(6.4845f, 3.5425f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(-0.65364f, 0.7568f))
            ),
        )

        @JvmStatic
        fun cosArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(-0.46926f, -0.47383f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(27.3082f, 0f))
            ),
        )

        @JvmStatic
        fun sinArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(1.02535f, -0.21685f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(0f, -27.2899f))
            ),
        )

        @JvmStatic
        fun tanArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(-0.85088f, 1.32129f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(0f, -0.99933f))
            ),
        )

        @JvmStatic
        fun coshArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(3.30164f, 1.73881f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(-0.65364f, 0f))
            ),
        )

        @JvmStatic
        fun sinhArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(3.18287f, 1.80369f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(0f, 0.7568f))
            ),
        )

        @JvmStatic
        fun tanhArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(0.97994f, 0.03022f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(0f, -1.15782f))
            ),
        )

        @JvmStatic
        fun sqrtArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(1.42505f, 0.17543f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(1.41421f, -1.41421f))
            ),
        )

        @JvmStatic
        fun plusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(2f, 4f)),
            ),
            Arguments.of(
                Wrapper(ComplexF(Float.POSITIVE_INFINITY, -1f)),
                Wrapper(ComplexF(Float.POSITIVE_INFINITY, -1f)),
            ),
            Arguments.of(
                Wrapper(ComplexF(-1f, Float.NEGATIVE_INFINITY)),
                Wrapper(ComplexF(-1f, Float.NEGATIVE_INFINITY)),
            ),
        )

        @JvmStatic
        fun minusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(-2f, -4f)),
            ),
            Arguments.of(
                Wrapper(ComplexF(Float.POSITIVE_INFINITY, -1f)),
                Wrapper(ComplexF(Float.NEGATIVE_INFINITY, 1f)),
            ),
            Arguments.of(
                Wrapper(ComplexF(-1f, Float.NEGATIVE_INFINITY)),
                Wrapper(ComplexF(1f, Float.POSITIVE_INFINITY)),
            ),
        )

        @JvmStatic
        fun complexPlusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(8f, -4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)),
                Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(-1.2f, -2.3f))
            ),
        )

        @JvmStatic
        fun complexPlusFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)), 6f,
                Wrapper(ComplexF(8f, 4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)), -1.2f,
                Wrapper(ComplexF(-1.2f, -2.3f))
            ),
        )

        @JvmStatic
        fun floatPlusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                2f, Wrapper(ComplexF(4f, 6f)),
                Wrapper(ComplexF(6f, 6f))
            ),
            Arguments.of(
                0f, Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(-1.2f, 0f))
            ),
        )

        @JvmStatic
        fun complexMinusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(-4f, 12f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)),
                Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(1.2f, -2.3f))
            ),
        )

        @JvmStatic
        fun complexMinusFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)), 6f,
                Wrapper(ComplexF(-4f, 4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)), -1.2f,
                Wrapper(ComplexF(1.2f, -2.3f))
            ),
        )

        @JvmStatic
        fun floatMinusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                2f, Wrapper(ComplexF(4f, 6f)),
                Wrapper(ComplexF(-2f, -6f))
            ),
            Arguments.of(
                0f, Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(1.2f, 0f))
            ),
        )

        @JvmStatic
        fun complexTimesComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(44f, 8f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)),
                Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(0f, 2.76f))
            ),
            Arguments.of(
                Wrapper(ComplexF(4.2f, 0f)),
                Wrapper(ComplexF(0f, 0.8f)),
                Wrapper(ComplexF(0f, 3.36f))
            ),
        )

        @JvmStatic
        fun complexTimesFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)), 6f,
                Wrapper(ComplexF(12f, 24f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)), -1.2f,
                Wrapper(ComplexF(0f, 2.76f))
            ),
            Arguments.of(
                Wrapper(ComplexF(4.2f, 0f)), -0.525f,
                Wrapper(ComplexF(-2.205f, 0f))
            ),
        )

        @JvmStatic
        fun floatTimesComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                2f, Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(12f, -16f))
            ),
            Arguments.of(
                0f, Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(0f, 0f))
            ),
            Arguments.of(
                4.2f, Wrapper(ComplexF(0f, 0.8f)),
                Wrapper(ComplexF(0f, 3.36f))
            ),
        )

        @JvmStatic
        fun complexDivComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(-0.2f, 0.4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)),
                Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(0f, 1.91667f))
            ),
            Arguments.of(
                Wrapper(ComplexF(4.2f, 0f)),
                Wrapper(ComplexF(0f, 0.8f)),
                Wrapper(ComplexF(0f, -5.25f))
            ),
        )

        @JvmStatic
        fun complexDivFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)), 6f,
                Wrapper(ComplexF(0.33333f, 0.66667f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)), -1.2f,
                Wrapper(ComplexF(0f, 1.91667f))
            ),
            Arguments.of(
                Wrapper(ComplexF(4.2f, 0f)), -0.525f,
                Wrapper(ComplexF(-8f, 0f))
            ),
        )

        @JvmStatic
        fun floatDivComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                2f, Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(0.12f, 0.16f))
            ),
            Arguments.of(
                0f, Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(0f, 0f))
            ),
            Arguments.of(
                4.2f, Wrapper(ComplexF(0f, 0.8f)),
                Wrapper(ComplexF(0f, -5.25f))
            ),
        )
    }
}