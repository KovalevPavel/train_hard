package kovp.trainhard.training_calendar_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import kovp.trainhard.navigation.ComposableScreen
import kovp.trainhard.training_calendar_presentation.TrainingCalendar

@Serializable
data class TrainingCalendarScreen(val lastAvailableDate: Long) {
    companion object : ComposableScreen {
        override fun createScreen(navGraphBuilder: NavGraphBuilder, navController: NavController) {
            navGraphBuilder.composable<TrainingCalendarScreen> { TrainingCalendar(navController = navController) }
        }
    }
}
