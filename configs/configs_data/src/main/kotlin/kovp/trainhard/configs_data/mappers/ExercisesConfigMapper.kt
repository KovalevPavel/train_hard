package kovp.trainhard.configs_data.mappers

import kovp.trainhard.configs_core.ExercisesConfig
import kovp.trainhard.configs_core.getMuscleByFullId
import kovp.trainhard.configs_data.ExercisesConfigDto
import kovp.trainhard.core_domain.MuscleGroup

class ExercisesConfigMapper {
    fun mapConfig(dto: ExercisesConfigDto): ExercisesConfig {
        val muscles = dto.muscles.flatMap { (group, raw) ->
            raw?.mapNotNull { (muscleId, name) ->
                ExercisesConfig.MuscleVo(
                    id = "${group}_$muscleId",
                    localizedString = name.orEmpty(),
                    muscleGroup = MuscleGroup.findById(group) ?: return@mapNotNull null,
                )
            }
                .orEmpty()
        }

        return ExercisesConfig(
            muscleGroups = dto.muscleGroups?.entries?.mapNotNull { (key, v) ->
                val k = MuscleGroup.findById(key) ?: return@mapNotNull null
                k to v
            }
                ?.toMap()
                .orEmpty(),
            muscles = muscles,
            defaultExercises = dto.defaultExercises.mapNotNull {
                ExercisesConfig.DefaultExercise(
                    title = it.title ?: return@mapNotNull null,
                    muscles = it.muscles?.mapNotNull(muscles::getMuscleByFullId)
                        ?: return@mapNotNull null,
                )
            }
        )
    }
}
