package kovp.trainhard.parameters_presentation.parameters.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kovp.trainhard.components.exercise_type.ExerciseCard
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersAction
import kovp.trainhard.parameters_presentation.parameters.presentation.ParametersScreenState

@Composable
fun DataContent(
    state: ParametersScreenState.Data,
    handleAction: (ParametersAction) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 16.dp,
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
