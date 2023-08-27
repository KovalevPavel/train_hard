package me.kovp.trainhard.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import me.kovp.trainhard.database.type_formatters.CompletedExercisesListConverter

@Entity(tableName = "completedExercises", primaryKeys = ["date", "exerciseId"])
@TypeConverters(
    CompletedExercisesListConverter::class,
)
data class CompletedSetEntity(
    /**
     * Дата, когда было сделано упражнение в формате dd.MM.yyyy
     */
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "exerciseId") val exerciseId: String,
    @ColumnInfo(name = "reps") val reps: List<Pair<Float, Int>>,
)
