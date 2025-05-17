package kovp.trainhard.database.entities

import androidx.room.ColumnInfo

data class MusclesInDayTuple(
    @ColumnInfo(name = "timestamp") val timestamp: Long?,
    @ColumnInfo(name = "muscles") val muscles: String?,
)
