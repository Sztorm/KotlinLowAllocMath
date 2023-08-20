package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.abs
import kotlin.math.withSign

class MutableRectangle(
    center: Vector2F, rotation: ComplexF, width: Float, height: Float
) : Rectangle {
    private var _center: Vector2F = center
    private var _rotation: ComplexF = rotation
    private var _width: Float = width
    private var _height: Float = height
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F
    private var _pointD: Vector2F

    init {
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val pA = ComplexF(halfWidth, halfHeight)
        val pB = ComplexF(-halfWidth, halfHeight)
        val pC = ComplexF(-halfWidth, -halfHeight)
        val pD = ComplexF(halfWidth, -halfHeight)
        _pointA = center + (rotation * pA).toVector2F()
        _pointB = center + (rotation * pB).toVector2F()
        _pointC = center + (rotation * pC).toVector2F()
        _pointD = center + (rotation * pD).toVector2F()
    }

    override val center: Vector2F
        get() = _center

    override val rotation: ComplexF
        get() = _rotation

    override val width: Float
        get() = _width

    override val height: Float
        get() = _height

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val pointD: Vector2F
        get() = _pointD

    override val area: Float
        get() = _width * _height

    override val perimeter: Float
        get() {
            val w: Float = _width
            val h: Float = _height

            return w + w + h + h
        }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val center: Vector2F = _center
        val rotation: ComplexF = _rotation
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p2 = ComplexF(
            if (abs(p1X) > halfWidth) halfWidth.withSign(p1X) else p1X,
            if (abs(p1Y) > halfHeight) halfHeight.withSign(p1Y) else p1Y
        )
        return center + (rotation * p2).toVector2F()
    }

    override operator fun contains(point: Vector2F): Boolean {
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val center: Vector2F = _center
        val p1 = ComplexF.conjugate(_rotation) *
                ComplexF(point.x - center.x, point.y - center.y)

        return (abs(p1.real) <= halfWidth) and (abs(p1.imaginary) <= halfHeight)
    }
}