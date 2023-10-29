package kovp.trainhard.app

import kovp.trainhard.components.selectors.DateRangeSelectorState
import kovp.trainhard.core_dialogs.AlertConfirmationDialogScreen
import kovp.trainhard.home_presentation.SelectGymDatesScreen
import kovp.trainhard.home_presentation.destinations.GymCardDatesDialogDestination
import kovp.trainhard.navigation_api.AppScreenMapper
import kovp.trainhard.new_training_api.NewSetDialogScreen
import kovp.trainhard.new_training_api.SelectExerciseTypeScreen
import kovp.trainhard.new_training_api.TrainingScreen
import kovp.trainhard.new_training_presentation.destinations.NewSetDialogDestination
import kovp.trainhard.new_training_presentation.destinations.SelectNewExerciseTypeComposableDestination
import kovp.trainhard.new_training_presentation.destinations.TrainingComposableDestination
import kovp.trainhard.parameters_api.NewExerciseDialogScreen
import kovp.trainhard.parameters_presentation.data.ExerciseScreenArgument
import kovp.trainhard.parameters_presentation.destinations.AlertConfirmationDialogDestination
import kovp.trainhard.parameters_presentation.destinations.NewExerciseScreenDestination
import kovp.trainhard.training_calendar_api.TrainingCalendarScreen
import kovp.trainhard.training_calendar_presentation.destinations.TrainingCalendarDestination

val appScreenMapper = AppScreenMapper {
    when (it) {
        is TrainingScreen -> {
            TrainingComposableDestination(currentTimestamp = it.timestamp)
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

        is TrainingCalendarScreen -> {
            TrainingCalendarDestination
        }

        is SelectGymDatesScreen -> {
            GymCardDatesDialogDestination(
                initDateRangeState = DateRangeSelectorState(
                    startTimestamp = it.startDate,
                    endTimestamp = it.endDate,
                )
            )
        }

        //TODO: сделать что-то с диалогами. В текущей версии либы их нельзя вынести в отдельный
        // модуль
        is AlertConfirmationDialogScreen -> {
            AlertConfirmationDialogDestination(
                arguments = it,
            )
        }

        else -> error("Can't find destination for screen $it")
    }
}
