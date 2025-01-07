package kovp.trainhard.configs_data.mappers

import kovp.trainhard.configs_core.ExercisesConfig
import kovp.trainhard.configs_data.ExercisesConfigDto
import kovp.trainhard.core_domain.Muscles

class ExercisesConfigMapper {
    fun mapConfig(dto: ExercisesConfigDto): ExercisesConfig {
        return ExercisesConfig(
            defaultExercises = dto.defaultExercises.map {
                ExercisesConfig.DefaultExercise(
                    title = it.title,
                    muscles = it.muscles.mapNotNull(Muscles::getMuscleById),
                )
            }
        )
    }
}
