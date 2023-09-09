package me.kovp.trainhard.parameters_presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import me.kovp.trainhard.components.muscle_groups_cloud.MuscleGroupsCloud
import me.kovp.trainhard.core_domain.MuscleGroup
import timber.log.Timber

@Destination
@Composable
fun ParametersComposable() {
    val list = mutableListOf<String>()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            MuscleGroup.values()
                .forEach { group ->
                    MuscleGroupsCloud(
                        group = group,
                    ) { muscle, isChecked ->
                        muscle.id.let { id ->
                            if (isChecked) list.add(id) else list.remove(id)
                            Timber.e("list -> ${list.toList()}")
                        }
                    }
                }
        }
    }
}
