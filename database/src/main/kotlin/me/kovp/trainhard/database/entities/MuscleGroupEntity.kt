package me.kovp.trainhard.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musclegroup")
data class MuscleGroupEntity(
    @PrimaryKey val id: String,
    val title: String,
)
