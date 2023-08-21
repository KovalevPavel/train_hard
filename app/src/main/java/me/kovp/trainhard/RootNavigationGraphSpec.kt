package me.kovp.trainhard

import androidx.navigation.NavGraph
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import me.kovp.trainhard.home_presentation.HomeNavGraph
import me.kovp.trainhard.statistics_presentation.StatisticsNavGraph
import me.kovp.trainhard.parameters_presentation.ParametersNavGraph

object RootNavigationGraphSpec : NavGraphSpec {

    override val nestedNavGraphs: List<NavGraphSpec> = listOf(
        HomeNavGraph,
        StatisticsNavGraph,
        ParametersNavGraph,
    )

    override val destinationsByRoute: Map<String, DestinationSpec<*>> = emptyMap()

    override val route: String = "root"
    override val startRoute: Route = HomeNavGraph

    val currentGraphFlow: Flow<Route>
        get() = _currentGraphFlow

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _currentGraphFlow = MutableSharedFlow<Route>()

    fun updateSelectedGraph(newGraph: NavGraphSpec) {
        scope.launch {
            _currentGraphFlow.emit(newGraph)
        }
    }
}
