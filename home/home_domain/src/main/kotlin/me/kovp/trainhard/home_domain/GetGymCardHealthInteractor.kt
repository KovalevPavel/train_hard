package me.kovp.trainhard.home_domain

import kotlin.random.Random

class GetGymCardHealthInteractor {
    operator fun invoke(): Float {
        return Random.nextInt(0, 100).toFloat() / 100f
    }
}