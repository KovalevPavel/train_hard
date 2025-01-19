package kovp.trainhard.configs_data.mappers

import kovp.trainhard.configs_core.ExercisesConfig
import kovp.trainhard.configs_data.ExercisesConfigDto
import kovp.trainhard.core_domain.Muscles

class ExercisesConfigMapper {
    fun mapConfig(dto: ExercisesConfigDto): ExercisesConfig {
        return ExercisesConfig(
            defaultExercises = dto.defaultExercises.mapNotNull {
                ExercisesConfig.DefaultExercise(
                    title = it.title ?: return@mapNotNull null,
                    muscles = it.muscles?.mapNotNull(Muscles::getMuscleByFullId)
                        ?: return@mapNotNull null,
                )
            }
        )
    }
}
