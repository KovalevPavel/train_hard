package kovp.trainhard.database.exercises

import kovp.trainhard.configs_core.ConfigHolder
import kovp.trainhard.configs_core.ExercisesConfig
import kovp.trainhard.database.entities.ExerciseEntity
import kovp.trainhard.database_api.models.ExerciseVo

internal class ExerciseMapper(
    private val configHolder: ConfigHolder,
) {
    fun mapToDb(data: ExerciseVo): ExerciseEntity = ExerciseEntity(
        title = data.title,
        muscles = data.muscles
            .map(ExercisesConfig.MuscleVo::id)
    )

    fun mapToDomain(data: ExerciseEntity): ExerciseVo {
        val groupsIds = configHolder.exercisesConfig.muscles.map(ExercisesConfig.MuscleVo::id)
        val exerciseGroups = mutableListOf<ExercisesConfig.MuscleVo>()

        data.muscles.forEach { id ->
            if (id in groupsIds) {
                configHolder.exercisesConfig.muscles
                    .firstOrNull { g -> g.id == id }
                    ?.let(exerciseGroups::add)
            }
        }

        return ExerciseVo(
            title = data.title,
            muscles = exerciseGroups
        )
    }
}
