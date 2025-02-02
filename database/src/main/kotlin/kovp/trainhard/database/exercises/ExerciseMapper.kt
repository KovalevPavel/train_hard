package kovp.trainhard.database.exercises

import kovp.trainhard.core_domain.Muscle
import kovp.trainhard.core_domain.Muscles
import kovp.trainhard.database.entities.ExerciseEntity
import kovp.trainhard.database_api.models.ExerciseVo

internal class ExerciseMapper {
    fun mapToDb(data: ExerciseVo): ExerciseEntity = ExerciseEntity(
        title = data.title,
        muscles = data.muscles
            .map(Muscle::id)
    )

    fun mapToDomain(data: ExerciseEntity): ExerciseVo {
        val groupsIds = Muscles.allMuscles.map(Muscle::id)
        val exerciseGroups = mutableListOf<Muscle>()

        data.muscles.forEach { id ->
            if (id in groupsIds) {
                Muscles.allMuscles
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
