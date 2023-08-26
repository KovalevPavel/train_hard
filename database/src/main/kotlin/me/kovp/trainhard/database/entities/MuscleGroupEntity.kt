package me.kovp.trainhard.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musclegroup")
data class MuscleGroupEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "groupId") val groupId: String,
)
