package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Vector2ITests {

    @ParameterizedTest
    @MethodSource("vectors")
    fun basicPropertiesAreValid(vector: Wrapper<Vector2I>) {
        val unwrappedVector: Vector2I = vector.value
        val (x, y) = unwrappedVector

        assertEquals(unwrappedVector.x, x)
        assertEquals(unwrappedVector.y, y)
        assertEquals(unwrappedVector.x, unwrappedVector[0])
        assertEquals(unwrappedVector.y, unwrappedVector[1])
        assertEquals(unwrappedVector.xy, Vector2I(x, y))
        assertEquals(unwrappedVector.yx, Vector2I(y, x))
        assertEquals(unwrappedVector.xx, Vector2I(x, x))
        assertEquals(unwrappedVector.yy, Vector2I(y, y))
    }

    @ParameterizedTest
    @MethodSource("squaredMagnitudeArgs")
    fun squaredMagnitudeReturnsCorrectValue(vector: Wrapper<Vector2I>, expected: Float) =
        assertTrue(expected.isApproximately(vector.value.squaredMagnitude))

    @ParameterizedTest
    @MethodSource("magnitudeArgs")
    fun magnitudeReturnsCorrectValue(vector: Wrapper<Vector2I>, expected: Float) =
        assertTrue(expected.isApproximately(vector.value.magnitude))

    @ParameterizedTest
    @MethodSource("squaredDistanceToArgs")
    fun squaredDistanceToReturnsCorrectValue(
        vector: Wrapper<Vector2I>, other: Wrapper<Vector2I>, expected: Float
    ) = assertTrue(expected.isApproximately(vector.value.squaredDistanceTo(other.value)))

    @ParameterizedTest
    @MethodSource("distanceToArgs")
    fun distanceToReturnsCorrectValue(
        vector: Wrapper<Vector2I>, other: Wrapper<Vector2I>, expected: Float
    ) = assertTrue(expected.isApproximately(vector.value.distanceTo(other.value)))

    @ParameterizedTest
    @MethodSource("coerceInArgs")
    fun coerceInReturnsCorrectValue(
        vector: Wrapper<Vector2I>,
        minimum: Wrapper<Vector2I>,
        maximum: Wrapper<Vector2I>,
        expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, vector.value.coerceIn(minimum.value, maximum.value))

    @ParameterizedTest
    @MethodSource("coerceAtLeastArgs")
    fun coerceAtLeastReturnsCorrectValue(
        a: Wrapper<Vector2I>, b: Wrapper<Vector2I>, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, a.value.coerceAtLeast(b.value))

    @ParameterizedTest
    @MethodSource("coerceAtMostArgs")
    fun coerceAtMostReturnsCorrectValue(
        a: Wrapper<Vector2I>, b: Wrapper<Vector2I>, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, a.value.coerceAtMost(b.value))

    @ParameterizedTest
    @MethodSource("dotArgs")
    fun dotReturnsCorrectValue(a: Wrapper<Vector2I>, b: Wrapper<Vector2I>, expected: Long) =
        assertEquals(expected, a.value dot b.value)

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(vector: Wrapper<Vector2I>, expected: String) =
        assertEquals(expected, vector.value.toString())

    @ParameterizedTest
    @MethodSource("toVector2FArg")
    fun toVector2FReturnsCorrectValue(vector: Wrapper<Vector2I>, expected: Wrapper<Vector2F>) =
        assertTrue(expected.value.isApproximately(vector.value.toVector2F()))

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(vector: Wrapper<Vector2I>, expected: Collection<Int>) {
        val actual: List<Int> = listOf(vector.value[0], vector.value[1])

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("vectors")
    fun getThrowsWhenIndexIsOutOfBounds(vector: Wrapper<Vector2I>) {
        assertThrows<IndexOutOfBoundsException> { vector.value[-1] }
        assertThrows<IndexOutOfBoundsException> { vector.value[2] }
    }

    @ParameterizedTest
    @MethodSource("plusVectorArgs")
    fun unaryPlusReturnsCorrectValue(vector: Wrapper<Vector2I>, expected: Wrapper<Vector2I>) =
        assertEquals(expected.value, +vector.value)

    @ParameterizedTest
    @MethodSource("minusVectorArgs")
    fun unaryMinusReturnsCorrectValue(vector: Wrapper<Vector2I>, expected: Wrapper<Vector2I>) =
        assertEquals(expected.value, -vector.value)

    @ParameterizedTest
    @MethodSource("vectorPlusVectorArgs")
    fun vectorPlusVectorReturnCorrectValue(
        a: Wrapper<Vector2I>, b: Wrapper<Vector2I>, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, a.value + b.value)

    @ParameterizedTest
    @MethodSource("vectorMinusVectorArgs")
    fun vectorMinusVectorReturnCorrectValue(
        a: Wrapper<Vector2I>, b: Wrapper<Vector2I>, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, a.value - b.value)

    @ParameterizedTest
    @MethodSource("vectorTimesVectorArgs")
    fun vectorTimesVectorReturnCorrectValue(
        a: Wrapper<Vector2I>, b: Wrapper<Vector2I>, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, a.value * b.value)

    @ParameterizedTest
    @MethodSource("vectorTimesFloatArgs")
    fun vectorTimesFloatReturnCorrectValue(
        a: Wrapper<Vector2I>, b: Int, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, a.value * b)

    @ParameterizedTest
    @MethodSource("floatTimesVectorArgs")
    fun floatTimesVectorReturnCorrectValue(
        a: Int, b: Wrapper<Vector2I>, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, a * b.value)

    @ParameterizedTest
    @MethodSource("vectorDivVectorArgs")
    fun vectorDivVectorReturnCorrectValue(
        a: Wrapper<Vector2I>, b: Wrapper<Vector2I>, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, a.value / b.value)

    @ParameterizedTest
    @MethodSource("vectorDivFloatArgs")
    fun vectorDivFloatReturnCorrectValue(
        a: Wrapper<Vector2I>, b: Int, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, a.value / b)

    @ParameterizedTest
    @MethodSource("maxArgs")
    fun maxReturnsCorrectValue(
        a: Wrapper<Vector2I>, b: Wrapper<Vector2I>, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, Vector2I.max(a.value, b.value))

    @ParameterizedTest
    @MethodSource("minArgs")
    fun minReturnsCorrectValue(
        a: Wrapper<Vector2I>, b: Wrapper<Vector2I>, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, Vector2I.min(a.value, b.value))

    companion object {
        @JvmStatic
        fun vectors(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2I(2, 4))),
            Arguments.of(Wrapper(Vector2I(2, 0))),
            Arguments.of(Wrapper(Vector2I(Int.MIN_VALUE, -1))),
            Arguments.of(Wrapper(Vector2I(-1, Int.MAX_VALUE))),
        )

        @JvmStatic
        fun squaredMagnitudeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2I(3, 4)), 25),
            Arguments.of(Wrapper(Vector2I(1, -2)), 5),
            Arguments.of(Wrapper(Vector2I.ZERO), 0),
        )

        @JvmStatic
        fun magnitudeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2I(3, 4)), 5f),
            Arguments.of(Wrapper(Vector2I(1, -2)), 2.236068f),
            Arguments.of(Wrapper(Vector2I.ZERO), 0f),
        )

        @JvmStatic
        fun squaredDistanceToArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(3, 4)), Wrapper(Vector2I(0, 4)), 9f
            ),
            Arguments.of(
                Wrapper(Vector2I(1, -2)), Wrapper(Vector2I(-4, 0)), 29f
            ),
            Arguments.of(
                Wrapper(Vector2I.ZERO), Wrapper(Vector2I.ZERO), 0f
            ),
        )

        @JvmStatic
        fun distanceToArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(3, 4)), Wrapper(Vector2I(0, 4)), 3f
            ),
            Arguments.of(
                Wrapper(Vector2I(1, -2)), Wrapper(Vector2I(-4, 0)), 5.385165f
            ),
            Arguments.of(
                Wrapper(Vector2I.ZERO), Wrapper(Vector2I.ZERO), 0f
            ),
        )

        @JvmStatic
        fun coerceInArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(3, 4)),
                Wrapper(Vector2I(0, 5)),
                Wrapper(Vector2I(2, 10)),
                Wrapper(Vector2I(2, 5)),
            ),
            Arguments.of(
                Wrapper(Vector2I(-3, 3)),
                Wrapper(Vector2I(-2, -2)),
                Wrapper(Vector2I(4, 0)),
                Wrapper(Vector2I(-2, 0)),
            ),
            Arguments.of(
                Wrapper(Vector2I(3, 4)),
                Wrapper(Vector2I(0, 0)),
                Wrapper(Vector2I(0, 0)),
                Wrapper(Vector2I(0, 0)),
            ),
        )

        @JvmStatic
        fun coerceAtLeastArgs(): List<Arguments> = maxArgs()

        @JvmStatic
        fun coerceAtMostArgs(): List<Arguments> = minArgs()

        @JvmStatic
        fun dotArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2I(3, 4)), Wrapper(Vector2I(0, 4)), 16L),
            Arguments.of(Wrapper(Vector2I(1, -2)), Wrapper(Vector2I(-4, 3)), -10L),
            Arguments.of(Wrapper(Vector2I(3, 4)), Wrapper(Vector2I.ZERO), 0L),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(2, 4)),
                "Vector2I(x=${2}, y=${4})"
            ),
            Arguments.of(
                Wrapper(Vector2I(-2, 0)),
                "Vector2I(x=${-2}, y=${0})"
            ),
            Arguments.of(
                Wrapper(Vector2I(Int.MIN_VALUE, -1)),
                "Vector2I(x=${Int.MIN_VALUE}, y=${-1})"
            ),
            Arguments.of(
                Wrapper(Vector2I(-1, Int.MAX_VALUE)),
                "Vector2I(x=${-1}, y=${Int.MAX_VALUE})"
            ),
        )

        @JvmStatic
        fun toVector2FArg(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(3, -4)), Wrapper(Vector2F(3f, -4f))
            ),
            Arguments.of(
                Wrapper(Vector2I(Int.MIN_VALUE, Int.MAX_VALUE)),
                Wrapper(Vector2F(Int.MIN_VALUE.toFloat(), Int.MAX_VALUE.toFloat()))
            ),
            Arguments.of(
                Wrapper(Vector2I.ZERO), Wrapper(Vector2F.ZERO)
            ),
        )

        @JvmStatic
        fun getArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(2, 4)), listOf(2, 4)
            ),
            Arguments.of(
                Wrapper(Vector2I(-2, 0)), listOf(-2, 0)
            ),
            Arguments.of(
                Wrapper(Vector2I(Int.MIN_VALUE, -1)),
                listOf(Int.MIN_VALUE, -1)
            ),
            Arguments.of(
                Wrapper(Vector2I(-1, Int.MAX_VALUE)),
                listOf(-1, Int.MAX_VALUE)
            ),
        )

        @JvmStatic
        fun plusVectorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(2, 4)), Wrapper(Vector2I(2, 4))
            ),
            Arguments.of(
                Wrapper(Vector2I(Int.MIN_VALUE, Int.MAX_VALUE)),
                Wrapper(Vector2I(Int.MIN_VALUE, Int.MAX_VALUE))
            )
        )

        @JvmStatic
        fun minusVectorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(2, 4)), Wrapper(Vector2I(-2, -4))
            ),
            Arguments.of(
                Wrapper(Vector2I(-Int.MAX_VALUE, Int.MAX_VALUE)),
                Wrapper(Vector2I(Int.MAX_VALUE, -Int.MAX_VALUE))
            )
        )

        @JvmStatic
        fun vectorPlusVectorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(2, 4)),
                Wrapper(Vector2I(-3, 1)),
                Wrapper(Vector2I(-1, 5)),
            ),
            Arguments.of(
                Wrapper(Vector2I(1, -2)),
                Wrapper(Vector2I(-4, 0)),
                Wrapper(Vector2I(-3, -2)),
            ),
        )

        @JvmStatic
        fun vectorMinusVectorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(2, 4)),
                Wrapper(Vector2I(-3, 1)),
                Wrapper(Vector2I(5, 3)),
            ),
            Arguments.of(
                Wrapper(Vector2I(1, -2)),
                Wrapper(Vector2I(-4, 0)),
                Wrapper(Vector2I(5, -2)),
            ),
        )

        @JvmStatic
        fun vectorTimesVectorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(2, 4)),
                Wrapper(Vector2I(-3, 1)),
                Wrapper(Vector2I(-6, 4)),
            ),
            Arguments.of(
                Wrapper(Vector2I(-1, -2)),
                Wrapper(Vector2I(-4, 0)),
                Wrapper(Vector2I(4, 0)),
            ),
        )

        @JvmStatic
        fun floatTimesVectorArgs(): List<Arguments> = listOf(
            Arguments.of(
                2,
                Wrapper(Vector2I(-3, 1)),
                Wrapper(Vector2I(-6, 2)),
            ),
            Arguments.of(
                0,
                Wrapper(Vector2I(-4, 0)),
                Wrapper(Vector2I(0, 0)),
            ),
        )

        @JvmStatic
        fun vectorTimesFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(-3, 1)),
                2,
                Wrapper(Vector2I(-6, 2)),
            ),
            Arguments.of(
                Wrapper(Vector2I(-4, 0)),
                0,
                Wrapper(Vector2I(0, 0)),
            ),
        )

        @JvmStatic
        fun vectorDivVectorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(2, 4)),
                Wrapper(Vector2I(-3, 1)),
                Wrapper(Vector2I(0, 4)),
            ),
            Arguments.of(
                Wrapper(Vector2I(-1, -5)),
                Wrapper(Vector2I(-4, 2)),
                Wrapper(Vector2I(0, -2)),
            ),
        )

        @JvmStatic
        fun vectorDivFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(2, 4)),
                -3,
                Wrapper(Vector2I(0, -1)),
            ),
            Arguments.of(
                Wrapper(Vector2I(-4, 2)),
                -1,
                Wrapper(Vector2I(4, -2)),
            ),
        )

        @JvmStatic
        fun maxArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(3, 4)),
                Wrapper(Vector2I(2, 10)),
                Wrapper(Vector2I(3, 10))
            ),
            Arguments.of(
                Wrapper(Vector2I(-3, 0)),
                Wrapper(Vector2I(0, 1)),
                Wrapper(Vector2I(0, 1))
            ),
        )

        @JvmStatic
        fun minArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2I(3, 4)),
                Wrapper(Vector2I(2, 10)),
                Wrapper(Vector2I(2, 4))
            ),
            Arguments.of(
                Wrapper(Vector2I(-3, 0)),
                Wrapper(Vector2I(0, 1)),
                Wrapper(Vector2I(-3, 0))
            ),
        )
    }
}