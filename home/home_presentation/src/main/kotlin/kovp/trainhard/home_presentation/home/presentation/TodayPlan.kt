package kovp.trainhard.home_presentation.home.presentation

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

sealed interface TodayPlan {

    data class TrainingDay(
        val items: ImmutableList<ExerciseCardVs>,
    ) : TodayPlan {

        @Immutable
        data class ExerciseCardVs(
            val id: String,
            val title: String,
            val sets: ImmutableList<String>,
            val muscleGroups: String,
        )
    }

    data object RestDay : TodayPlan

    data object NoProgramSelected : TodayPlan
}
