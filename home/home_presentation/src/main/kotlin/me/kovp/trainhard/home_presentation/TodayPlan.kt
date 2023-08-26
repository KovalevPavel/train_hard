package me.kovp.trainhard.home_presentation

sealed interface TodayPlan {

    data class TrainingDay(
        val items: List<Exercise>,
    ) : TodayPlan {
        data class Exercise(
            val id: String,
            val title: String,
            val sets: List<String>,
            val muscleGroups: String,
        )
    }

    object RestDay : TodayPlan

    object NoProgramSelected : TodayPlan
}
