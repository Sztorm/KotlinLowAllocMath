package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.lerp
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

fun Circle(center: Vector2F, orientation: ComplexF, radius: Float): Circle =
    MutableCircle(center, orientation, radius)

interface Circle : CircleShape, Transformable {
    val center: Vector2F

    override val area: Float
        get() = PI.toFloat() * radius * radius

    override val perimeter: Float
        get() = 2f * PI.toFloat() * radius

    override val diameter: Float
        get() = 2f * radius

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): Circle = copy(center = center + offset)

    override fun movedTo(position: Vector2F): Circle = copy(center = position)

    override fun rotatedBy(rotation: AngleF): Circle =
        copy(orientation = orientation * ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Circle = copy(orientation = orientation * rotation)

    override fun rotatedTo(orientation: AngleF): Circle =
        copy(orientation = ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Circle = copy(orientation = orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Circle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = center
        val (startRotR: Float, startRotI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY

        return copy(
            center = Vector2F(
                cpDiffX * rotR - cpDiffY * rotI + pX,
                cpDiffY * rotR + cpDiffX * rotI + pY
            ),
            orientation = ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            )
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Circle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Circle =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Circle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = center
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        return if (centerToPointDist > 0.00001f) {
            val startRotR: Float = cpDiffX / centerToPointDist
            val startRotI: Float = cpDiffY / centerToPointDist

            copy(
                center = Vector2F(
                    rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
                ),
                orientation = ComplexF(startRotR, -startRotI) * this.orientation * orientation
            )
        } else {
            copy(orientation = orientation)
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Circle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Circle =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): Circle = copy(
        orientation = orientation * 1f.withSign(factor),
        radius = radius * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): Circle {
        val (cX: Float, cY: Float) = center
        val (pX: Float, pY: Float) = point

        return copy(
            center = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY)),
            orientation = orientation * 1f.withSign(factor),
            radius = radius * factor.absoluteValue
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): Circle = copy(
        center = center + offset,
        orientation = orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Circle = copy(
        center = center + offset,
        orientation = orientation * rotation
    )

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Circle = copy(
        center = center + offset,
        orientation = orientation * ComplexF.fromAngle(rotation) * 1f.withSign(factor),
        radius = radius * factor.absoluteValue
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Circle = copy(
        center = center + offset,
        orientation = orientation * rotation * 1f.withSign(factor),
        radius = radius * factor.absoluteValue
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): Circle = copy(
        center = position,
        orientation = ComplexF.fromAngle(orientation)
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Circle = copy(
        center = position,
        orientation = orientation
    )

    fun interpolated(to: Circle, by: Float): Circle = copy(
        center = Vector2F.lerp(center, to.center, by),
        orientation = ComplexF.slerp(orientation, to.orientation, by),
        radius = Float.lerp(radius, to.radius, by)
    )

    fun closestPointTo(point: Vector2F): Vector2F {
        val radius: Float = radius
        val (cx: Float, cy: Float) = center
        val dx: Float = point.x - cx
        val dy: Float = point.y - cy
        val distance: Float = sqrt(dx * dx + dy * dy)
        val t: Float = radius / distance

        return if (distance > radius) Vector2F(cx + dx * t, cy + dy * t)
        else point
    }

    fun intersects(annulus: Annulus): Boolean {
        val distance: Float = center.distanceTo(annulus.center)
        val radius: Float = radius

        return (distance >= (annulus.innerRadius - radius)) and
                (distance <= (annulus.outerRadius + radius))
    }

    fun intersects(circle: Circle): Boolean =
        center.distanceTo(circle.center) <= radius + circle.radius

    operator fun contains(point: Vector2F): Boolean = center.distanceTo(point) <= radius

    operator fun contains(annulus: Annulus): Boolean =
        center.distanceTo(annulus.center) <= radius - annulus.outerRadius

    operator fun contains(circle: Circle): Boolean =
        center.distanceTo(circle.center) <= radius - circle.radius

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        radius: Float = this.radius
    ): Circle

    operator fun component1(): Vector2F = center

    operator fun component2(): ComplexF = orientation

    operator fun component3(): Float = radius
}