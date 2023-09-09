package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

class MutableRay(origin: Vector2F, direction: Vector2F) : Ray {
    private var _origin: Vector2F = origin
    private var _direction: Vector2F = direction

    override val origin: Vector2F
        get() = _origin

    override val direction: Vector2F
        get() = _direction

    override fun copy(origin: Vector2F, direction: Vector2F) =
        MutableRay(origin, direction)

    override fun equals(other: Any?): Boolean = other is Ray &&
            _origin == other.origin &&
            _direction == other.direction

    fun equals(other: MutableRay): Boolean =
        _origin == other._origin && _direction == other._direction

    override fun hashCode(): Int {
        val originHash: Int = _origin.hashCode()
        val directionHash: Int = _direction.hashCode()

        return originHash * 31 + directionHash
    }

    override fun toString() =
        StringBuilder("Ray(origin=").append(_origin)
            .append(", direction=").append(_direction).append(")")
            .toString()

    override operator fun component1(): Vector2F = _origin

    override operator fun component2(): Vector2F = _direction
}