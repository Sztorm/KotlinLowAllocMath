package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CircleTests {
    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(circle: Circle, expected: Float) =
        assertTrue(expected.isApproximately(circle.area))

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(circle: Circle, expected: Float) =
        assertTrue(expected.isApproximately(circle.perimeter))

    @ParameterizedTest
    @MethodSource("diameterArgs")
    fun diameterReturnsCorrectValue(circle: Circle, expected: Float) =
        assertTrue(expected.isApproximately(circle.diameter))

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        circle: Circle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertTrue(expected.value.isApproximately(circle.closestPointTo(point.value)))

    @ParameterizedTest
    @MethodSource("intersectsCircleArgs")
    fun intersectsReturnsCorrectValue(circle: Circle, otherCircle: Circle, expected: Boolean) =
        assertEquals(expected, circle.intersects(otherCircle))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(circle: Circle, point: Wrapper<Vector2F>, expected: Boolean) =
        assertEquals(expected, circle.contains(point.value))

    @ParameterizedTest
    @MethodSource("containsCircleArgs")
    fun containsReturnsCorrectValue(circle: Circle, otherCircle: Circle, expected: Boolean) =
        assertEquals(expected, circle.contains(otherCircle))

    companion object {
        @JvmStatic
        fun areaArgs(): List<Arguments> = listOf(
            Arguments.of(Circle(center = Vector2F.ZERO, radius = 4f), 50.2655f),
            Arguments.of(Circle(center = Vector2F(1f, 2f), radius = 4f), 50.2655f),
            Arguments.of(Circle(center = Vector2F.ZERO, radius = 2.5f), 19.635f),
        )

        @JvmStatic
        fun perimeterArgs(): List<Arguments> = listOf(
            Arguments.of(Circle(center = Vector2F.ZERO, radius = 4f), 25.1327f),
            Arguments.of(Circle(center = Vector2F(1f, 2f), radius = 4f), 25.1327f),
            Arguments.of(Circle(center = Vector2F.ZERO, radius = 2.5f), 15.708f),
        )

        @JvmStatic
        fun diameterArgs(): List<Arguments> = listOf(
            Arguments.of(Circle(center = Vector2F.ZERO, radius = 4f), 8f),
            Arguments.of(Circle(center = Vector2F(1f, 2f), radius = 4f), 8f),
            Arguments.of(Circle(center = Vector2F.ZERO, radius = 2.5f), 5f),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), radius = 4f),
                Wrapper(Vector2F.ZERO),
                Wrapper(Vector2F.ZERO)
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), radius = 4f),
                Wrapper(Vector2F(-2.1f, 0f)),
                Wrapper(Vector2F(-2f, 0f))
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), radius = 4f),
                Wrapper(Vector2F(-2f, 4f)),
                Wrapper(Vector2F(-0.828429f, 2.828429f))
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), radius = 4f),
                Wrapper(Vector2F(0f, 2f)),
                Wrapper(Vector2F(0f, 2f))
            ),
        )

        @JvmStatic
        fun intersectsCircleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F(-4f, 4f), radius = 4f),
                Circle(center = Vector2F(2f, 0f), radius = 3f),
                false
            ),
            Arguments.of(
                Circle(center = Vector2F(-4f, 4f), radius = 4f),
                Circle(center = Vector2F(2f, 0f), radius = 4f),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F(-4f, 4f), radius = 4f),
                Circle(center = Vector2F(4f, 4f), radius = 4f),
                true
            ),
        )

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F.ZERO, radius = 4f), Wrapper(Vector2F.ZERO), true
            ),
            Arguments.of(
                Circle(center = Vector2F.ZERO, radius = 4f), Wrapper(Vector2F(4f, 0f)), true
            ),
            Arguments.of(
                Circle(center = Vector2F.ZERO, radius = 4f),
                Wrapper(Vector2F(4.1f, 0f)),
                false
            ),
        )

        @JvmStatic
        fun containsCircleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F.ZERO, radius = 4f),
                Circle(center = Vector2F.ZERO, radius = 3.9f),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), radius = 4f),
                Circle(center = Vector2F(4f, 0f), radius = 2f),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), radius = 4f),
                Circle(center = Vector2F(4f, 0f), radius = 2.1f),
                false
            ),
        )
    }
}