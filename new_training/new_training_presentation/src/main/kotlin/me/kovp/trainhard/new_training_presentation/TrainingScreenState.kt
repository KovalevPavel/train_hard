package me.kovp.trainhard.new_training_presentation

data class TrainingScreenState(
    val timeElapsed: Long,
) {
    companion object {
        val init = TrainingScreenState(
            timeElapsed = 0,
        )
    }
}
