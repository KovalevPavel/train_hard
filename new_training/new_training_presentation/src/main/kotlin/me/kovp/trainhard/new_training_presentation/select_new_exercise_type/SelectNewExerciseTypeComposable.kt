package me.kovp.trainhard.new_training_presentation.select_new_exercise_type

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.getOr
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import me.kovp.new_training_presentation.R
import me.kovp.trainhard.components.text_field.TrainTextField
import me.kovp.trainhard.core_design.joinToStringComposable
import me.kovp.trainhard.core_design.mapMuscleTitle
import me.kovp.trainhard.database_api.models.Exercise
import me.kovp.trainhard.navigation_api.localScreenMapper
import me.kovp.trainhard.new_training_api.NewSetDialogScreen
import me.kovp.trainhard.new_training_api.NewSetDialogScreen.RequestAction
import me.kovp.trainhard.new_training_presentation.destinations.NewSetDialogDestination
import me.kovp.trainhard.new_training_presentation.di.selectExerciseModule
import me.kovp.trainhard.new_training_presentation.new_set_dialog.NewSetDialogResult
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun SelectNewExerciseTypeComposable(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<NewSetDialogResult>,
    resultRecipient: ResultRecipient<NewSetDialogDestination, NewSetDialogResult>,
) {
    loadKoinModules(selectExerciseModule)
    val screenMapper = localScreenMapper.current

    resultRecipient.onNavResult {
        val result = it.getOr { NewSetDialogResult.Error }
        resultNavigator.navigateBack(result)
    }

    val viewModel: SelectNewExerciseTypeViewModel =
        koinViewModel<SelectNewExerciseTypeViewModelImpl>()

    val screenState by viewModel.screenState
        .collectAsState(initial = SelectExerciseScreenState.init)

    ExerciseList(screenState = screenState) {
        val newSetDialog = screenMapper(
            NewSetDialogScreen(
                exerciseTitle = it.title,
                requestAction = RequestAction.ADD,
            )
        )
        navigator.navigate(newSetDialog)
    }

    DisposableEffect(key1 = viewModel) {
        onDispose {
            unloadKoinModules(selectExerciseModule)
        }
    }
}

@Composable
private fun ExerciseList(
    screenState: SelectExerciseScreenState,
    onItemClick: (Exercise) -> Unit,
) {
    var currentQuery by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (screenState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(top = 64.dp),
                color = themeColors.lime,
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (screenState.isLoading.not()) {
                item {
                    TrainTextField(
                        value = currentQuery,
                        hint = stringResource(id = R.string.seacrch_exercise_hint),
                        onValueChanged = { currentQuery = it },
                    )
                }
            }

            itemsIndexed(
                items = screenState.items
                    .filter { it.title.contains(currentQuery, ignoreCase = true) },
                key = { _, item -> item.title },
            ) { index, item ->
                ExerciseItem(item = item, onItemClick = onItemClick)
                if (index < screenState.items.lastIndex) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseItem(
    modifier: Modifier = Modifier,
    item: Exercise,
    onItemClick: (Exercise) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
    ) {
        Text(
            text = item.title,
            style = themeTypography.header2,
        )
        Text(
            text = item.muscles
                .joinToStringComposable { mapMuscleTitle(muscleId = it.id) },
            style = themeTypography.body1,
        )
    }
}
