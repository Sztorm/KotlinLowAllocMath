package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class RectangleTests {
    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        rectangle: RectangleShape,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>,
        expectedPointD: Wrapper<Vector2F>
    ) {
        assertApproximation(expectedPointA.value, rectangle.pointA)
        assertApproximation(expectedPointB.value, rectangle.pointB)
        assertApproximation(expectedPointC.value, rectangle.pointC)
        assertApproximation(expectedPointD.value, rectangle.pointD)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(rectangle: RectangleShape, expected: Float) =
        assertApproximation(expected, rectangle.area)

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(rectangle: RectangleShape, expected: Float) =
        assertApproximation(expected, rectangle.perimeter)

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        rectangle: Rectangle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, rectangle.closestPointTo(point.value))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        rectangle: Rectangle, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertEquals(expected, rectangle.contains(point.value))

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        rectangle: Rectangle,
        center: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        expected: Rectangle
    ) = assertEquals(expected, rectangle.copy(center.value, rotation.value, width, height))

    @ParameterizedTest
    @MethodSource("equalsArgs")
    fun equalsReturnsCorrectValue(
        rectangle: MutableRectangle, other: Any?, expected: Boolean
    ) = assertEquals(expected, rectangle == other)

    @ParameterizedTest
    @MethodSource("equalsMutableRectangleArgs")
    fun equalsReturnsCorrectValue(
        rectangle: MutableRectangle, other: MutableRectangle, expected: Boolean
    ) = assertEquals(expected, rectangle.equals(other))

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(rectangle: MutableRectangle, other: MutableRectangle) =
        assertEquals(rectangle.hashCode(), other.hashCode())

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(rectangle: MutableRectangle, expected: String) =
        assertEquals(expected, rectangle.toString())

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        rectangle: Rectangle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float,
        expectedComponent4: Float
    ) {
        val (actualComponent1, actualComponent2, actualComponent3, actualComponent4) = rectangle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
        assertEquals(expectedComponent4, actualComponent4)
    }

    companion object {
        @JvmStatic
        fun pointsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Rectangle(center = Vector2F.ZERO, rotation = ComplexF.ONE, width = 4f, height = 2f),
                Wrapper(Vector2F(2f, 1f)),
                Wrapper(Vector2F(-2f, 1f)),
                Wrapper(Vector2F(-2f, -1f)),
                Wrapper(Vector2F(2f, -1f))
            ),
            Arguments.of(
                Rectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                Wrapper(Vector2F(-1.292893f, 6.12132f)),
                Wrapper(Vector2F(-4.12132f, 3.292893f)),
                Wrapper(Vector2F(-2.707107f, 1.87868f)),
                Wrapper(Vector2F(0.12132f, 4.707107f))
            ),
            Arguments.of(
                Rectangle(
                    center = Vector2F(-1f, -2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    width = 3f,
                    height = 5f
                ),
                Wrapper(Vector2F(-3.915063f, -1.950962f)),
                Wrapper(Vector2F(-2.415063f, -4.549038f)),
                Wrapper(Vector2F(1.915063f, -2.049038f)),
                Wrapper(Vector2F(0.415063f, 0.549038f))
            ),
        )

        @JvmStatic
        fun areaArgs(): List<Arguments> = listOf(
            Arguments.of(
                Rectangle(center = Vector2F.ZERO, rotation = ComplexF.ONE, width = 4f, height = 2f),
                8f
            ),
            Arguments.of(
                Rectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                8f
            ),
            Arguments.of(
                Rectangle(
                    center = Vector2F.ZERO, rotation = ComplexF.ONE, width = 1.5f, height = 3f
                ),
                4.5f
            ),
        )

        @JvmStatic
        fun perimeterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Rectangle(center = Vector2F.ZERO, rotation = ComplexF.ONE, width = 4f, height = 2f),
                12f
            ),
            Arguments.of(
                Rectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                12f
            ),
            Arguments.of(
                Rectangle(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.ONE,
                    width = 1.5f,
                    height = 3f
                ),
                9f
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val pointA = Vector2F(2f, 7f)
            val pointB = Vector2F(-2f, 5f)
            val pointC = Vector2F(-1f, 3f)
            val pointD = Vector2F(3f, 5f)
            val a = (pointA.y - pointB.y) / (pointA.x - pointB.x)
            val rectangle = Rectangle(
                center = (pointB + pointD) * 0.5f,
                rotation = ComplexF.fromAngle(AngleF.atan(a)),
                width = pointA.distanceTo(pointB),
                height = pointB.distanceTo(pointC)
            )
            return listOf(
                Arguments.of(
                    rectangle, Wrapper(Vector2F(4.5f, 7f)), Wrapper(Vector2F(2.5f, 6f))
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(2.1f, 7.1f)), Wrapper(Vector2F(2f, 7f))
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(0.5f, 8f)), Wrapper(Vector2F(1.2f, 6.6f))
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-5f, 6f)), Wrapper(Vector2F(-2f, 5f))
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-3.5f, 3f)), Wrapper(Vector2F(-1.5f, 4f))
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-0.1f, 1f)), Wrapper(Vector2F(-1f, 3f))
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(1.5f, 3f)), Wrapper(Vector2F(1f, 4f))
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(5f, 4f)), Wrapper(Vector2F(3f, 5f))
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(1.2f, 6.5f)), Wrapper(Vector2F(1.2f, 6.5f))
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1f, 3.1f)), Wrapper(Vector2F(-1f, 3.1f))
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1.4f, 4f)), Wrapper(Vector2F(-1.4f, 4f))
                ),
            )
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val pointA = Vector2F(2f, 7f)
            val pointB = Vector2F(-2f, 5f)
            val pointC = Vector2F(-1f, 3f)
            val pointD = Vector2F(3f, 5f)
            val a = (pointA.y - pointB.y) / (pointA.x - pointB.x)
            val rectangle = Rectangle(
                center = (pointB + pointD) * 0.5f,
                rotation = ComplexF.fromAngle(AngleF.atan(a)),
                width = pointA.distanceTo(pointB),
                height = pointB.distanceTo(pointC)
            )
            return listOf(
                Arguments.of(
                    rectangle, Wrapper(Vector2F(1.2f, 6.5f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(1.2f, 6.7f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1f, 3.1f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-0.9f, 3f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1.4f, 4f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1.6f, 4f)), false
                ),
                Arguments.of(
                    Rectangle(
                        center = Vector2F.ZERO, rotation = ComplexF.ONE, width = 4f, height = 2f
                    ),
                    Wrapper(Vector2F(-1.9f, -0.9f)),
                    true
                ),
                Arguments.of(
                    Rectangle(
                        center = Vector2F.ZERO, rotation = ComplexF.ONE, width = 4f, height = 2f
                    ),
                    Wrapper(Vector2F(-2.1f, -1f)),
                    false
                ),
                Arguments.of(
                    Rectangle(
                        center = Vector2F(-2f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(0.02132f, 4.707107f)),
                    true
                ),
                Arguments.of(
                    Rectangle(
                        center = Vector2F(-2f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(0.22132f, 4.707107f)),
                    false
                ),
            )
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Rectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                Wrapper(Vector2F(-2f, 4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                4f,
                2f,
                Rectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                Rectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                Wrapper(Vector2F(-2.5f, 1f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                4f,
                1f,
                Rectangle(
                    center = Vector2F(-2.5f, 1f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 1f
                )
            ),
            Arguments.of(
                Rectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                Wrapper(Vector2F(0f, 8f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                1f,
                3f,
                Rectangle(
                    center = Vector2F(0f, 8f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                )
            ),
        )

        @JvmStatic
        fun equalsArgs(): List<Arguments> = equalsMutableRectangleArgs() + listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                null,
                false
            ),
        )

        @JvmStatic
        fun equalsMutableRectangleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                true
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2.1f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(0f, 8f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                ),
                MutableRectangle(
                    center = Vector2F(0f, 8f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                "Rectangle(" +
                        "center=${Vector2F(-2f, 4f)}, " +
                        "rotation=${ComplexF.fromAngle(AngleF.fromDegrees(45f))}, " +
                        "width=${4f}, " +
                        "height=${2f})"
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(0f, 8f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                ),
                "Rectangle(" +
                        "center=${Vector2F(0f, 8f)}, " +
                        "rotation=${ComplexF.fromAngle(AngleF.fromDegrees(30f))}, " +
                        "width=${1f}, " +
                        "height=${3f})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Rectangle(
                    center = Vector2F(-2f, 4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                Wrapper(Vector2F(-2f, 4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                4f,
                2f
            ),
            Arguments.of(
                Rectangle(
                    center = Vector2F(0f, 8f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                ),
                Wrapper(Vector2F(0f, 8f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                1f,
                3f
            ),
        )
    }
}