package me.kovp.trainhard.navigation_api

import com.ramcosta.composedestinations.spec.Direction

fun interface AppScreenMapper {
    operator fun invoke(screen: Screen): Direction
}
