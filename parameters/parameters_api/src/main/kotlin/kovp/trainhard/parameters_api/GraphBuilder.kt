package kovp.trainhard.parameters_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import kovp.trainhard.parameters_presentation.ParametersComposable

@Serializable
data object ParametersBaseRoute

@Serializable
data object ParametersRoute

fun NavGraphBuilder.parametersSection() {
    navigation<ParametersBaseRoute>(startDestination = ParametersRoute) {
        composable<ParametersRoute> {
            ParametersComposable()
        }
    }
}

fun NavController.navigateToParams(options: NavOptions) = navigate(ParametersRoute, options)
