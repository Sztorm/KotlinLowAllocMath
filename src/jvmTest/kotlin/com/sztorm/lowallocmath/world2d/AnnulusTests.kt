package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AnnulusTests {
    @ParameterizedTest
    @MethodSource("annularRadiusArgs")
    fun annularRadiusReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertTrue(expected.isApproximately(annulus.annularRadius))

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertTrue(expected.isApproximately(annulus.area))

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertTrue(expected.isApproximately(annulus.perimeter))

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        annulus: Annulus, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertTrue(expected.value.isApproximately(annulus.closestPointTo(point.value)))

    @ParameterizedTest
    @MethodSource("intersectsCircleArgs")
    fun intersectsReturnsCorrectValue(
        annulus: Annulus, circle: CircleShape, expected: Boolean
    ) = assertEquals(expected, annulus.intersects(circle))

    @ParameterizedTest
    @MethodSource("intersectsAnnulusArgs")
    fun intersectsReturnsCorrectValue(
        annulus: Annulus, otherAnnulus: AnnulusShape, expected: Boolean
    ) = assertEquals(expected, annulus.intersects(otherAnnulus))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        annulus: Annulus, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertEquals(expected, annulus.contains(point.value))

    @ParameterizedTest
    @MethodSource("containsCircleArgs")
    fun containsReturnsCorrectValue(
        annulus: Annulus, circle: CircleShape, expected: Boolean
    ) = assertEquals(expected, annulus.contains(circle))

    @ParameterizedTest
    @MethodSource("containsAnnulusArgs")
    fun containsReturnsCorrectValue(
        annulus: Annulus, otherAnnulus: AnnulusShape, expected: Boolean
    ) = assertEquals(expected, annulus.contains(otherAnnulus))

    companion object {
        @JvmStatic
        fun annularRadiusArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(center = Vector2F.ZERO, outerRadius = 4f, innerRadius = 2f),
                2f
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                2f
            ),
            Arguments.of(
                Annulus(center = Vector2F.ZERO, outerRadius = 2.5f, innerRadius = 1.5f),
                1f
            ),
        )

        @JvmStatic
        fun areaArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(center = Vector2F.ZERO, outerRadius = 4f, innerRadius = 2f),
                37.6991f
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                37.6991f
            ),
            Arguments.of(
                Annulus(center = Vector2F.ZERO, outerRadius = 2.5f, innerRadius = 1.5f),
                12.5664f
            ),
        )

        @JvmStatic
        fun perimeterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(center = Vector2F.ZERO, outerRadius = 4f, innerRadius = 2f),
                37.6991f
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                37.6991f
            ),
            Arguments.of(
                Annulus(center = Vector2F.ZERO, outerRadius = 2.5f, innerRadius = 1.5f),
                25.1327f
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Wrapper(Vector2F(-2.9f, 2f)),
                Wrapper(Vector2F(-3f, 2f))
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Wrapper(Vector2F(-3.1f, 2f)),
                Wrapper(Vector2F(-3.1f, 2f))
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Wrapper(Vector2F(-4.9f, 2f)),
                Wrapper(Vector2F(-4.9f, 2f))
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Wrapper(Vector2F(-5.1f, 2f)),
                Wrapper(Vector2F(-5f, 2f))
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Wrapper(Vector2F(-5f, 6f)),
                Wrapper(Vector2F(-3.828429f, 4.828429f))
            ),
        )

        @JvmStatic
        fun intersectsCircleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Circle(center = Vector2F(-2f, 2f), radius = 1.01f),
                true
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Circle(center = Vector2F(-2f, 2f), radius = 0.99f),
                false
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Circle(center = Vector2F(-6f, 2f), radius = 1.01f),
                true
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Circle(center = Vector2F(-6f, 2f), radius = 0.99f),
                false
            ),
        )

        @JvmStatic
        fun intersectsAnnulusArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Annulus(center = Vector2F(-2f, 2f), outerRadius = 1.01f, innerRadius = 0.5f),
                true
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Annulus(center = Vector2F(-2f, 2f), outerRadius = 0.99f, innerRadius = 0.5f),
                false
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Annulus(center = Vector2F(-6f, 2f), outerRadius = 1.01f, innerRadius = 0.5f),
                true
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Annulus(center = Vector2F(-6f, 2f), outerRadius = 0.99f, innerRadius = 0.5f),
                false
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Annulus(center = Vector2F(0f, 2f), outerRadius = 7f, innerRadius = 4.9f),
                true
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Annulus(center = Vector2F(0f, 2f), outerRadius = 7f, innerRadius = 5.1f),
                false
            ),
        )

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Wrapper(Vector2F(-3.01f, 2f)),
                true
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Wrapper(Vector2F(-2.99f, 2f)),
                false
            ),
            Arguments.of(
                Annulus(center = Vector2F.ZERO, outerRadius = 2.5f, innerRadius = 1.5f),
                Wrapper(Vector2F(0f, 2.49f)),
                true
            ),
            Arguments.of(
                Annulus(center = Vector2F.ZERO, outerRadius = 2.5f, innerRadius = 1.5f),
                Wrapper(Vector2F(0f, 2.51f)),
                false
            ),
        )

        @JvmStatic
        fun containsCircleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Circle(center = Vector2F(-1f, -1f), radius = 0.99f),
                true
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Circle(center = Vector2F(-1f, -1f), radius = 1.01f),
                false
            ),
        )

        @JvmStatic
        fun containsAnnulusArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Annulus(center = Vector2F(-1f, -1f), outerRadius = 0.99f, innerRadius = 0.5f),
                true
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Annulus(center = Vector2F(-1f, -1f), outerRadius = 1.01f, innerRadius = 0.5f),
                false
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 3.99f, innerRadius = 2.01f),
                true
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4.01f, innerRadius = 2.01f),
                false
            ),
            Arguments.of(
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 4f, innerRadius = 2f),
                Annulus(center = Vector2F(-1f, 2f), outerRadius = 3.99f, innerRadius = 1.99f),
                false
            ),
        )
    }
}