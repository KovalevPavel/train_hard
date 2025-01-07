package kovp.trainhard.app

import kovp.trainhard.app.domain.ConfigRepository
import kovp.trainhard.database_api.ExercisesApi

class InitBaseExercisesInteractor(
    private val exercisesApi: ExercisesApi,
    private val configRepository: ConfigRepository,
) {
    suspend operator fun invoke() {
        configRepository.getExercisesConfig()
            .defaultExercises
            .let {
                exercisesApi.addInitExercises(it)
            }
    }
}
