package me.kovp.trainhard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.kovp.trainhard.database.dao.CalendarDao
import me.kovp.trainhard.database.dao.CompletedExercisesDao
import me.kovp.trainhard.database.dao.ExerciseDao
import me.kovp.trainhard.database.entities.CompletedExerciseEntity
import me.kovp.trainhard.database.entities.ExerciseEntity

@Database(
    entities = [
        ExerciseEntity::class,
        CompletedExerciseEntity::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exercisesDao(): ExerciseDao
    abstract fun completedExercisesDao(): CompletedExercisesDao
    abstract fun calendarDao(): CalendarDao
}
