package me.kovp.trainhard

import me.kovp.trainhard.navigation_api.AppScreenMapper
import me.kovp.trainhard.new_training_api.NewSetDialogScreen
import me.kovp.trainhard.new_training_api.SelectExerciseTypeScreen
import me.kovp.trainhard.new_training_api.TrainingScreen
import me.kovp.trainhard.new_training_presentation.destinations.NewSetDialogDestination
import me.kovp.trainhard.new_training_presentation.destinations.SelectNewExerciseTypeComposableDestination
import me.kovp.trainhard.new_training_presentation.destinations.TrainingComposableDestination
import me.kovp.trainhard.parameters_api.NewExerciseDialogScreen
import me.kovp.trainhard.parameters_presentation.data.ExerciseScreenArgument
import me.kovp.trainhard.parameters_presentation.destinations.NewExerciseScreenDestination
import trainhard.core_dialogs.AlertConfirmationDialogScreen
import trainhard.core_dialogs.destinations.AlertConfirmationDialogDestination

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

        is NewExerciseDialogScreen -> {
            NewExerciseScreenDestination(
                argument = ExerciseScreenArgument(
                    title = it.cardTitle,
                    muscleIds = it.muscleIds,
                ),
                requestAction = it.requestAction,
            )
        }

        is AlertConfirmationDialogScreen -> {
            AlertConfirmationDialogDestination(
                arguments = it,
            )
        }

        else -> error("Can't find destination for screen $it")
    }
}
