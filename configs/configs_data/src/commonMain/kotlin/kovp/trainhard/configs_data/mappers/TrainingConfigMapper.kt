package kovp.trainhard.configs_data.mappers

import kovp.trainhard.configs_core.TrainingConfig
import kovp.trainhard.configs_data.TrainingConfigDto

class TrainingConfigMapper {
    fun mapConfig(dto: TrainingConfigDto): TrainingConfig {
        return TrainingConfig(
            weightIncrement = dto.weightIncrement ?: DEFAULT_WEIGHT_INCREMENT,
            firstMonthOffset = dto.firstMonthOffset ?: Int.MAX_VALUE,
            startYear = dto.startYear ?: DEFAULT_START_YEAR,
        )
    }

    companion object {
        private const val DEFAULT_WEIGHT_INCREMENT = 2.5f
        private const val DEFAULT_START_YEAR = 2022
    }
}
