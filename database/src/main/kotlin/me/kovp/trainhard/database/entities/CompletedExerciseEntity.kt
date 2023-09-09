package me.kovp.trainhard.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import me.kovp.trainhard.database.type_formatters.CompletedExercisesListConverter
import me.kovp.trainhard.database_api.models.Sets

/**
 * Завершенное упражнение в тренировке (упражнение + дата + сеты)
 * @param id Используется как порядковый номер упражнения типа [exerciseId] в текущей тренировке
 * @param date Дата, когда было сделано упражнение в формате dd.MM.yyyy
 * @param exerciseId id упражнения
 * @param sets Список сетов
 */
@Entity(tableName = "completedExercises", primaryKeys = ["id", "date", "exerciseId"])
@TypeConverters(
    CompletedExercisesListConverter::class,
)
data class CompletedExerciseEntity(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "exerciseId") val exerciseId: String,
    @ColumnInfo(name = "sets") val sets: Sets,
)
