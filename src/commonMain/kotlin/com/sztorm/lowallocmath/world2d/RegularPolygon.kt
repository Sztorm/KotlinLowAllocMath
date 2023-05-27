@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

data class RegularPolygon(
    val circumcenter: Vector2F, val circumradius: Float, val rotation: Float, val sideCount: Int
)