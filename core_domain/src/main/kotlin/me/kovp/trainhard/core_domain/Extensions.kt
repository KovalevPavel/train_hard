@file:Suppress("unused")
package me.kovp.trainhard.core_domain

fun Int?.orZero() = this ?: 0
fun Float?.orZero() = this ?: 0
fun Boolean?.orFalse() = this ?: false
