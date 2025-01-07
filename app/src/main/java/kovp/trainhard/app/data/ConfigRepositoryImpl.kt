package kovp.trainhard.app.data

import kotlinx.serialization.json.Json
import kovp.trainhard.app.R
import kovp.trainhard.app.domain.ConfigRepository
import kovp.trainhard.app.domain.ExercisesConfig
import kovp.trainhard.core_domain.Muscles
import kovp.trainhard.database_api.models.ExerciseVo
import trainhard.kovp.core.ResourceProvider

class ConfigRepositoryImpl(
    private val resourceProvider: ResourceProvider,
): ConfigRepository {
    override fun getExercisesConfig(): ExercisesConfig {
        val rawString = resourceProvider.getConfig(R.raw.exercises_config)
        val dto = Json.decodeFromString<ExercisesConfigDto>(rawString)
        return ExercisesConfig(
            defaultExercises = dto.defaultExercises.map { ex ->
                ExerciseVo(
                    title = ex.title,
                    muscles = ex.muscles.mapNotNull(Muscles::getMuscleById),
                )
            }
        )
    }
}
