@file:Suppress("OVERRIDE_BY_INLINE")

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.withSign

class MutableRegularTriangle(
    center: Vector2F, rotation: ComplexF, sideLength: Float
) : RegularTriangle {
    private var _center: Vector2F = center
    private var _rotation: ComplexF = rotation
    private var _sideLength: Float = sideLength
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F

    init {
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = this.inradius
        val circumradius: Float = inradius + inradius
        val rotationR: Float = rotation.real
        val rotationI: Float = rotation.imaginary
        val pB = ComplexF(-halfSideLength, -inradius)
        val pC = ComplexF(halfSideLength, -inradius)
        _pointA = center + Vector2F(-rotationI * circumradius, rotationR * circumradius)
        _pointB = center + (rotation * pB).toVector2F()
        _pointC = center + (rotation * pC).toVector2F()
    }

    override val center: Vector2F
        get() = _center

    override val rotation: ComplexF
        get() = _rotation

    override val sideLength: Float
        get() = _sideLength

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val area: Float
        get() = 0.4330127f * _sideLength * _sideLength

    override val perimeter: Float
        get() = 3f * _sideLength

    override inline val sideCount: Int
        get() = 3

    override inline val interiorAngle: AngleF
        get() = AngleF((PI / 3.0).toFloat())

    override inline val exteriorAngle: AngleF
        get() = AngleF((2.0 / 3.0 * PI).toFloat())

    override val inradius: Float
        get() = 0.28867513f * _sideLength

    override val circumradius: Float
        get() = 0.5773503f * _sideLength

    override fun closestPointTo(point: Vector2F): Vector2F {
        val halfSideLength: Float = _sideLength * 0.5f
        val inradius: Float = this.inradius
        val rotation: ComplexF = _rotation
        val center: Vector2F = _center
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val yGB: Float = 0.5773503f * p1X // (sqrt(3) / 3) * x
        val yGC: Float = -yGB // (-sqrt(3) / 3) * x

        if ((p1Y <= yGB) and (p1Y <= yGC)) {
            if ((p1Y >= -inradius)) {
                return point
            }
            if (p1X.absoluteValue >= halfSideLength) {
                val vertexPoint = ComplexF(halfSideLength.withSign(p1X), -inradius)

                return (rotation * vertexPoint).toVector2F() + center
            }
            val edgePoint = ComplexF(p1X, -inradius)

            return (rotation * edgePoint).toVector2F() + center
        }
        val add120DegRotation = ComplexF(-0.5f, -(0.8660254f.withSign(p1X)))
        val p2: ComplexF = add120DegRotation * p1
        val p2X: Float = p2.real
        val p2Y: Float = p2.imaginary

        if ((p2Y >= -inradius)) {
            return point
        }
        if (p2X.absoluteValue >= halfSideLength) {
            val vertexPoint = ComplexF(halfSideLength.withSign(p2X), -inradius)

            return (ComplexF.conjugate(add120DegRotation) * rotation * vertexPoint)
                .toVector2F() + center
        }
        val edgePoint = ComplexF(p2X, -inradius)

        return (ComplexF.conjugate(add120DegRotation) * rotation * edgePoint)
            .toVector2F() + center
    }

    override operator fun contains(point: Vector2F): Boolean {
        val sideLength: Float = _sideLength
        val minusInradius: Float = -0.28867513f * sideLength // sqrt(3) / 6 * a
        val p1 = ComplexF.conjugate(_rotation) *
                ComplexF(point.x - _center.x, point.y - _center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        //yAB = sqrt(3) * x + (sqrt(3) / 3) * a
        //yAC = -sqrt(3) * x + (sqrt(3) / 3) * a
        val ax: Float = 1.7320508f * p1X
        val b: Float = 0.5773503f * sideLength
        val yAB: Float = ax + b
        val yAC: Float = -ax + b

        return (p1Y >= minusInradius) and (p1Y <= yAB) and (p1Y <= yAC)
    }

    override fun copy(center: Vector2F, rotation: ComplexF, sideLength: Float) =
        MutableRegularTriangle(center, rotation, sideLength)

    override fun equals(other: Any?): Boolean = other is RegularTriangle &&
            _center == other.center &&
            _rotation == other.rotation &&
            _sideLength == other.sideLength

    fun equals(other: MutableRegularTriangle): Boolean =
        _center == other._center &&
                _rotation == other._rotation &&
                _sideLength == other._sideLength

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val rotationHash: Int = _rotation.hashCode()
        val sideLengthHash: Int = _sideLength.hashCode()

        return centerHash * 961 + rotationHash * 31 + sideLengthHash
    }

    override fun toString() =
        StringBuilder("RegularTriangle(center=").append(_center)
            .append(", rotation=").append(_rotation)
            .append(", sideLength=").append(_sideLength).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _rotation

    override operator fun component3(): Float = _sideLength
}