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
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import kotlin.time.Duration.Companion.milliseconds

private const val MILLIS_IN_SECONDS = 1000
private const val SECONDS_IN_MINUTE = 60
private const val MINUTES_IN_HOUR = 60

private val myStoreOwner = object : ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore = ViewModelStore()
}

@Destination
@Composable
fun NewTrainingComposable(
    navigator: DestinationsNavigator,
) {
    loadKoinModules(newTrainingModule)
    val storeOwner = LocalViewModelStoreOwner.current
    println("local store -> $storeOwner\nmy store -> $myStoreOwner")

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
