package me.kovp.trainhard.database.exercises

import me.kovp.trainhard.database.entities.ExerciseEntity
import me.kovp.trainhard.database.entities.MuscleGroupEntity
import me.kovp.trainhard.database.muscle_groups.MuscleGroupsMapper
import me.kovp.trainhard.database_api.models.Exercise

internal class ExerciseMapper(
    private val muscleGroupMapper: MuscleGroupsMapper,
) {
    fun mapToDb(data: Exercise): ExerciseEntity = ExerciseEntity(
        title = data.title,
        muscleGroups = data.muscleGroups
            .mapIndexed(muscleGroupMapper::mapMuscleGroupToDb)
            .map(MuscleGroupEntity::id),
    )

    fun mapToDomain(groups: List<MuscleGroupEntity>, data: ExerciseEntity): Exercise {
        val groupsIds = groups.map(MuscleGroupEntity::id)
        val exerciseGroups = mutableListOf<String>()

        data.muscleGroups.forEach { id ->
            if (id in groupsIds) {
                groups.firstOrNull { g -> g.id == id }?.groupId?.let(exerciseGroups::add)
            }
        }

        return Exercise(
            title = data.title,
            muscleGroups = exerciseGroups.mapNotNull(muscleGroupMapper::mapMuscleGroupToDomainById)
        )
    }
}
