package kovp.trainhard.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import kovp.trainhard.database.type_formatters.CompletedExercisesListConverter
import kovp.trainhard.database_api.models.Sets

/**
 * Завершенное упражнение в тренировке (упражнение + дата + сеты)
 * @param id Используется как порядковый номер упражнения типа [exerciseId] в текущей тренировке
 * @param dayTimestamp timestamp, когда было сделано упражнение
 * @param exerciseId id упражнения
 * @param sets Список сетов
 */
@Entity(tableName = "completedExercises", primaryKeys = ["id", "dayTimestamp", "exerciseId"])
@TypeConverters(
    CompletedExercisesListConverter::class,
)
data class CompletedExerciseEntity(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "dayTimestamp") val dayTimestamp: Long,
    @ColumnInfo(name = "exerciseId") val exerciseId: String,
    @ColumnInfo(name = "sets") val sets: Sets,
)
