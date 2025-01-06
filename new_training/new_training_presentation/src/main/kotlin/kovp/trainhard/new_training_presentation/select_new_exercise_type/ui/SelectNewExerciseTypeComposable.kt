package kovp.trainhard.new_training_presentation.select_new_exercise_type.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kovp.trainhard.components.StateContainer
import kovp.trainhard.components.progress.FullscreenLoader
import kovp.trainhard.components.text_field.TrainTextField
import kovp.trainhard.navigation.SubscribeOnEvents
import kovp.trainhard.new_training_presentation.R
import kovp.trainhard.new_training_presentation.SelectExerciseTypeScreen
import kovp.trainhard.new_training_presentation.di.selectExerciseModule
import kovp.trainhard.new_training_presentation.select_new_exercise_type.ExerciseVs
import kovp.trainhard.new_training_presentation.select_new_exercise_type.SelectExerciseAction
import kovp.trainhard.new_training_presentation.select_new_exercise_type.SelectExerciseEvent
import kovp.trainhard.new_training_presentation.select_new_exercise_type.SelectExerciseScreenState
import kovp.trainhard.new_training_presentation.select_new_exercise_type.SelectNewExerciseTypeViewModel
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.module.rememberKoinModules
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun SelectNewExerciseTypeComposable(navController: NavController) {
    rememberKoinModules { listOf(selectExerciseModule) }

    val viewModel = koinViewModel<SelectNewExerciseTypeViewModel>()
    val state by viewModel.state.collectAsState()

    val onBackClick = remember {
        {
            handleEvent(
                event = SelectExerciseEvent.NavigateBack(),
                navController = navController,
            )
        }
    }

    BackHandler { onBackClick() }

    SubscribeOnEvents(
        eventFlow = viewModel.eventFlow,
        action = { handleEvent(event = it, navController = navController) },
    )

    Scaffold(
        containerColor = themeColors.black,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            tint = themeColors.white,
                            contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.select_exercise_title),
                        style = themeTypography.header1,
                        color = themeColors.white,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = themeColors.black),
            )
        },
    ) { paddings ->
        StateContainer(
            modifier = Modifier.padding(top = paddings.calculateTopPadding()),
            state = state,
        ) {
            when (val st = state) {
                is SelectExerciseScreenState.Loading -> {
                    FullscreenLoader()
                }

                is SelectExerciseScreenState.Data -> {
                    DataContent(screenState = st) {
                        SelectExerciseAction.OnExerciseClick(it).let(viewModel::handleAction)
                    }
                }
            }
        }
    }
}

@Composable
private fun DataContent(
    screenState: SelectExerciseScreenState.Data,
    onItemClick: (ExerciseVs) -> Unit,
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
                hint = stringResource(id = R.string.search_exercise_hint),
                onValueChanged = { currentQuery = it },
            )
        }

        items(
            key = { item -> item.title },
            items = screenState.items
                .filter { it.title.contains(currentQuery, ignoreCase = true) },
        ) { item ->
            ExerciseItem(
                item = item,
                onItemClick = { onItemClick(item) },
            )
        }
    }
}

private fun handleEvent(event: SelectExerciseEvent, navController: NavController) {
    when (event) {
        is SelectExerciseEvent.NavigateBack -> {
            event.exerciseTitle?.let {
                navController.previousBackStackEntry?.savedStateHandle
                    ?.set(SelectExerciseTypeScreen.EXERCISE_TITLE_ID, it)
            }
            navController.navigateUp()
        }
    }
}
