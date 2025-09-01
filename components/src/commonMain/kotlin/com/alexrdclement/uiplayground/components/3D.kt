package com.alexrdclement.uiplayground.components

data class Point3D(val x: Double, val y: Double, val z: Double)

fun rotatePoint3D(
    x: Double,
    y: Double,
    z: Double,
    cosX: Double,
    sinX: Double,
    cosY: Double,
    sinY: Double,
    cosZ: Double,
    sinZ: Double,
): Point3D {
    val y1 = y * cosX - z * sinX
    val z1 = y * sinX + z * cosX

    val x2 = x * cosY + z1 * sinY
    val z2 = -x * sinY + z1 * cosY

    val x3 = x2 * cosZ - y1 * sinZ
    val y3 = x2 * sinZ + y1 * cosZ

    return Point3D(x3, y3, z2)
}

data class ViewingAngle(
    val rotationX: Float = 0f,
    val rotationY: Float = 0f,
    val rotationZ: Float = 0f,
)
