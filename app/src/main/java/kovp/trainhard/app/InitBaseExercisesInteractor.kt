package kovp.trainhard.app

import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.database_api.ExercisesApi
import kovp.trainhard.database_api.models.ExerciseVo

class InitBaseExercisesInteractor(
    private val exercisesApi: ExercisesApi,
    private val configHolder: ConfigHolder,
) {
    suspend operator fun invoke() {
        configHolder.exercises
            .defaultExercises
            // todo: объединить модели?
            .map {
                ExerciseVo(title = it.title, muscles = it.muscles)
            }
            .let {
                exercisesApi.addInitExercises(it)
            }
    }
}
