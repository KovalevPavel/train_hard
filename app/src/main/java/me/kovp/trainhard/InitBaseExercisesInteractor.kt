package me.kovp.trainhard

import me.kovp.trainhard.database_api.ExercisesApi
import me.kovp.trainhard.database_api.models.Exercise
import me.kovp.trainhard.database_api.models.MuscleGroup

class InitBaseExercisesInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke() {
        listOf(
            Exercise(
                title = "Приседания",
                muscleGroups = listOf(
                    MuscleGroup.Legs.QUADRICEPS,
                ),
            ),
            Exercise(
                title = "Становая тяга",
                muscleGroups = listOf(
                    MuscleGroup.Legs.QUADRICEPS,
                    MuscleGroup.Legs.BICEPS,
                    MuscleGroup.Legs.CALVES,
                    MuscleGroup.Back.LATS,
                    MuscleGroup.Back.LOIN,
                    MuscleGroup.Back.TRAPEZIUS,
                    MuscleGroup.Arms.FOREARMS,
                    MuscleGroup.Abs,
                ),
            ),
            Exercise(
                title = "Жим лежа",
                muscleGroups = listOf(
                    MuscleGroup.Chest.UPPER_CHEST,
                    MuscleGroup.Chest.LOWER_CHEST,
                    MuscleGroup.Deltas.MIDDLE,
                    MuscleGroup.Deltas.POSTERIOR,
                    MuscleGroup.Arms.TRICEPS,
                ),
            ),
        )
            .let {
                exercisesApi.addInitExercises(it)
            }
    }
}
