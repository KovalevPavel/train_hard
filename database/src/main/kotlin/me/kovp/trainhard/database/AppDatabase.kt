package me.kovp.trainhard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.kovp.trainhard.database.dao.CompletedSetsDao
import me.kovp.trainhard.database.dao.ExerciseDao
import me.kovp.trainhard.database.dao.MuscleGroupDao
import me.kovp.trainhard.database.entities.CompletedSetEntity
import me.kovp.trainhard.database.entities.ExerciseEntity
import me.kovp.trainhard.database.entities.MuscleGroupEntity

@Database(
    entities = [
        MuscleGroupEntity::class,
        ExerciseEntity::class,
        CompletedSetEntity::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun muscleGroupsDao(): MuscleGroupDao
    abstract fun exercisesDao(): ExerciseDao
    abstract fun completedExercisesDao(): CompletedSetsDao
}
