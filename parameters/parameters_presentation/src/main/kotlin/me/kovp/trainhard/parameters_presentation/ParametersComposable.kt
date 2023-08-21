package me.kovp.trainhard.parameters_presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun ParametersComposable() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Parameters screen")
    }
}
