package kovp.trainhard.app

import kovp.trainhard.core_domain.Muscles
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.database_api.models.ExerciseVo

class InitBaseExercisesInteractor(
    private val exercisesApi: ExercisesApi,
) {
    suspend operator fun invoke() {
        listOf(
            ExerciseVo(
                title = "Приседания",
                muscles = listOf(
                    Muscles.quadriceps,
                ),
            ),
            ExerciseVo(
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
            ExerciseVo(
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
