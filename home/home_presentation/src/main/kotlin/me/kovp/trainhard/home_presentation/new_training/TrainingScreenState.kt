package me.kovp.trainhard.home_presentation.new_training

data class TrainingScreenState(
    val timeElapsed: Long,
) {
    companion object {
        val init = TrainingScreenState(
            timeElapsed = 0,
        )
    }
}
