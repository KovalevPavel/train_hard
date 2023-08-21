package me.kovp.trainhard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.kovp.trainhard.database.dao.MuscleGroupDao
import me.kovp.trainhard.database.entities.MuscleGroupEntity

@Database(
    entities = [MuscleGroupEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun muscleGroupsDao(): MuscleGroupDao
}
