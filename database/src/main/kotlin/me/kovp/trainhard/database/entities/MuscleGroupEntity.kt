package me.kovp.trainhard.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @param id id для записи в БД
 * @param groupId id мышечной группы. Является ключом для последующего маппинга в читаемый вид
 */
@Entity(tableName = "musclegroup")
data class MuscleGroupEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "groupId") val groupId: String,
)
