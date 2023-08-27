package me.kovp.trainhard.new_training_presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.kovp.trainhard.new_training_presentation.TrainingScreenState.SetItem
import me.kovp.trainhard.new_training_presentation.di.newTrainingModule
import me.kovp.trainhard.ui_theme.providers.themeColors
import me.kovp.trainhard.ui_theme.providers.themeTypography
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules

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

    val vm: NewTrainingViewModel =
        koinViewModel<NewTrainingViewModelImpl>(viewModelStoreOwner = myStoreOwner)

    val state by vm.screenState.collectAsState(initial = TrainingScreenState.init)

    Scaffold(
        floatingActionButton = {
            IconButton(onClick = { vm.addNewExercise() }) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = AbsoluteRoundedCornerShape(20.dp),
                    color = themeColors.lime
                ) {
                    Image(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
    ) { paddings ->
        LazyColumn(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .background(themeColors.black)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.items) {
                SetCard(item = it, onClick = remember { { vm.editSet(it) } })
            }
        }
    }
}

@Composable
private fun SetCard(
    item: SetItem,
    onClick: (SetItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = themeColors.gray,
                shape = RoundedCornerShape(16.dp),
            )
            .clickable { onClick(item) },
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = item.title,
            style = themeTypography.body1,
        )
        item.reps.forEach { set ->
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = set,
                style = themeTypography.body2,
            )
        }
        //        Text(
        //            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        //            text = stringResource(id = R.string.muscle_groups, exercise.muscleGroups),
        //            style = themeTypography.body2,
        //        )
    }
}
