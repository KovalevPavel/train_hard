package me.kovp.trainhard.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import me.kovp.trainhard.database.type_formatters.IntListConverter

/**
 * @param title Название упраженения. Используется как id
 * @param muscleGroups Список мышечных групп, которые работают в упражнении
 */
@Entity(tableName = "exercises")
@TypeConverters(
    IntListConverter::class,
)
data class ExerciseEntity(
    @PrimaryKey @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "muscleGroups") val muscleGroups: List<Int>,
)
