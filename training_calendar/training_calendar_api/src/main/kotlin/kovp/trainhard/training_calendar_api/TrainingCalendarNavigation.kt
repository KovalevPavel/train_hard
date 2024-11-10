package kovp.trainhard.training_calendar_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kovp.trainhard.training_calendar_presentation.TrainingCalendar

fun NavGraphBuilder.trainingCalendarFlow(navController: NavController) {
    composable<TrainingCalendarScreen> { TrainingCalendar(navController = navController) }
}
