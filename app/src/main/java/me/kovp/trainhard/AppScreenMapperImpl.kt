package me.kovp.trainhard

import me.kovp.trainhard.navigation_api.AppScreenMapper
import me.kovp.trainhard.new_training_api.NewSetDialogScreen
import me.kovp.trainhard.new_training_api.SelectExerciseTypeScreen
import me.kovp.trainhard.new_training_api.TrainingScreen
import me.kovp.trainhard.new_training_presentation.destinations.NewSetDialogDestination
import me.kovp.trainhard.new_training_presentation.destinations.SelectNewExerciseTypeComposableDestination
import me.kovp.trainhard.new_training_presentation.destinations.TrainingComposableDestination

val appScreenMapper = AppScreenMapper {
    when (it) {
        is TrainingScreen -> {
            TrainingComposableDestination(currentDateString = it.dateString)
        }

        is SelectExerciseTypeScreen -> {
            SelectNewExerciseTypeComposableDestination
        }

        is NewSetDialogScreen -> {
            NewSetDialogDestination(
                setId = it.id,
                repsId = it.setId,
                exerciseTitle = it.exerciseTitle,
                initWeight = it.initWeight,
                initReps = it.initReps,
                requestAction = it.requestAction,
            )
        }

        else -> error("Can't find destination for screen $it")
    }
}
