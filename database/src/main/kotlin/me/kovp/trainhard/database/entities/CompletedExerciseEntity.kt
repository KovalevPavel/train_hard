package me.kovp.trainhard.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import me.kovp.trainhard.database.type_formatters.CompletedExercisesListConverter

@Entity(tableName = "completedExercises")
@TypeConverters(
    CompletedExercisesListConverter::class,
)
data class CompletedExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "exerciseId") val exerciseId: String,
    @ColumnInfo(name = "reps") val reps: List<Pair<Float, Int>>,
)
