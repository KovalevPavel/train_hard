package kovp.trainhard.new_training_presentation.select_new_exercise_type

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.components.text_field.TrainTextField
import kovp.trainhard.components.joinToStringComposable
import kovp.trainhard.components.mapMuscleTitle
import kovp.trainhard.database_api.models.Exercise
import kovp.trainhard.new_training_presentation.R
import kovp.trainhard.new_training_presentation.di.selectExerciseModule
import kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@Composable
fun SelectNewExerciseTypeComposable() {
    loadKoinModules(selectExerciseModule)

//    resultRecipient.onNavResult {
//        val result = it.getOr { NewSetDialogResult.Error }
//        resultNavigator.navigateBack(result)
//    }

    val viewModel = koinViewModel<SelectNewExerciseTypeViewModel>()

    val action by viewModel.actionFlow.collectAsState(initial = SelectExerciseAction.Empty)

    when (val st = viewModel.state) {
        is SelectExerciseScreenState.Loading -> {
            FullscreenLoader()
        }

        is SelectExerciseScreenState.Data -> {
            DataContent(screenState = st) {
                SelectExerciseEvent.OnExerciseClick(it).let(viewModel::handleAction)
            }
        }
    }

//    SubscribeOnAction(action = action, navigator = navigator)

    DisposableEffect(key1 = viewModel) {
        onDispose {
            unloadKoinModules(selectExerciseModule)
        }
    }
}

//@Composable
//fun SubscribeOnAction(
//    action: SelectExerciseAction,
//    navigator: DestinationsNavigator,
//) {
//    val screenMapper = localScreenMapper.current
//
//    when (action) {
//        is SelectExerciseAction.Empty -> {
//            // do nothing
//        }
//
//        is SelectExerciseAction.NavigateToNewSetDialog -> {
//            screenMapper(screen = action.data)
//                .let(navigator::navigate)
//        }
//    }
//}

@Composable
private fun DataContent(
    screenState: SelectExerciseScreenState.Data,
    onItemClick: (Exercise) -> Unit,
) {
    var currentQuery by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TrainTextField(
                value = currentQuery,
                hint = stringResource(id = R.string.seacrch_exercise_hint),
                onValueChanged = { currentQuery = it },
            )
        }

        itemsIndexed(
            items = screenState.items
                .filter { it.title.contains(currentQuery, ignoreCase = true) },
            key = { _, item -> item.title },
        ) { index, item ->
            ExerciseItem(
                item = item,
                onItemClick = onItemClick,
            )

            if (index < screenState.items.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
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
