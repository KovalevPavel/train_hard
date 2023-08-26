package me.kovp.trainhard.new_training_presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.kovp.trainhard.new_training_presentation.di.newTrainingModule
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import kotlin.time.Duration.Companion.milliseconds

//TODO: перенести в MainActivity
private val myStoreOwner = object : ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore = ViewModelStore()
}

@Destination
@Composable
fun NewTrainingComposable(
    navigator: DestinationsNavigator,
) {
    loadKoinModules(newTrainingModule)

    val vm: NewTrainingViewModel = koinViewModel<NewTrainingViewModelImpl>(viewModelStoreOwner = myStoreOwner)

    val currentTime by vm.screenState.collectAsState(initial = TrainingScreenState.init)

    Column(
        modifier = Modifier
            .safeContentPadding()
            .background(
                color = themeColors.black,
            )
    ) {
        Text(
            text = currentTime.timeElapsed.formatElapsedTime(),
            style = themeTypography.header1,
        )
    }
}

private fun Long.formatElapsedTime(): String {
    fun String.addTrailingZero() = this.takeIf {
        it.toCharArray().size == 2
    }
        ?: "0$this"

    val totalMillis = this.milliseconds

    val hours = "${totalMillis.inWholeHours}".addTrailingZero()
    val minutes = "${totalMillis.inWholeMinutes % 60}".addTrailingZero()
    val seconds = "${totalMillis.inWholeSeconds % 60}".addTrailingZero()
    return "$hours:$minutes:$seconds"
}
