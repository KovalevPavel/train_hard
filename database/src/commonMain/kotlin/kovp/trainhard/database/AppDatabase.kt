package kovp.trainhard.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import kovp.trainhard.database.dao.ExerciseDao
import kovp.trainhard.database.dao.CalendarDao
import kovp.trainhard.database.dao.CompletedExercisesDao
import kovp.trainhard.database.entities.CompletedExerciseEntity
import kovp.trainhard.database.entities.ExerciseEntity

@ConstructedBy(AppDatabaseConstructor::class)
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

    companion object {
        internal const val DATABASE_NAME = "app_database"
    }
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor: RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
