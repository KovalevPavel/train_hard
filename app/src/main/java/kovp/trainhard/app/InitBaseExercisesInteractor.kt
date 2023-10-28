package kovp.trainhard.app

import kovp.trainhard.core_domain.Muscles
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.database_api.models.Exercise

class InitBaseExercisesInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke() {
        listOf(
            Exercise(
                title = "Приседания",
                muscles = listOf(
                    Muscles.quadriceps,
                ),
            ),
            Exercise(
                title = "Становая тяга",
                muscles = listOf(
                    Muscles.quadriceps,
                    Muscles.hamstrings,
                    Muscles.calves,
                    Muscles.lats,
                    Muscles.loin,
                    Muscles.trapezius,
                    Muscles.armsForearms,
                    Muscles.abs,
                ),
            ),
            Exercise(
                title = "Жим лежа",
                muscles = listOf(
                    Muscles.upperChest,
                    Muscles.lowerChest,
                    Muscles.deltoidsMid,
                    Muscles.deltoidsAnt,
                    Muscles.armsTriceps,
                ),
            ),
        )
            .let {
                exercisesApi.addInitExercises(it)
            }
    }
}
