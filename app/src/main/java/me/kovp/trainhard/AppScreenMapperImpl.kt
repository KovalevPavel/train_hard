package me.kovp.trainhard

import me.kovp.trainhard.navigation_api.AppScreenMapper
import me.kovp.trainhard.new_training_api.NewTrainingScreen
import me.kovp.trainhard.new_training_presentation.destinations.NewTrainingComposableDestination

val appScreenMapper = AppScreenMapper {
    when (it) {
        is NewTrainingScreen -> NewTrainingComposableDestination
        else -> error("Can't find destination for screen $it")
    }
}
