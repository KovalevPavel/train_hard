package kovp.trainhard.parameters_presentation.parameters.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kovp.trainhard.components.exercise_type.ExerciseCard
import kovp.trainhard.components.fab.TrainFab
import kovp.trainhard.parameters_presentation.R
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersAction
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersScreenState
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataContent(
    state: ParametersScreenState.Data,
    handleAction: (ParametersAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.exercises_list),
                        style = themeTypography.header1,
                        color = themeColors.lime,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = themeColors.black),
            )
        },
        floatingActionButton = {
            TrainFab(icon = Icons.Filled.Add) {
                handleAction(ParametersAction.OnAddButtonClick)
            }
        },
        containerColor = themeColors.black,
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 100.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(state.items) { dto ->
                ExerciseCard(
                    card = dto,
                    onCardClick = { exerciseCard ->
                        handleAction(
                            ParametersAction.OnExerciseClick(exercise = exerciseCard),
                        )
                    },
                    onRemoveClick = { exerciseCard ->
                        handleAction(
                            ParametersAction.OnDeleteExerciseClicked(exerciseCard)
                        )
                    },
                )
            }
        }
    }
}
