package kovp.trainhard.app.navigation_graphs

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kovp.trainhard.app.CurrentHostScreenFlowHolder
import kovp.trainhard.navigation_api.BottomNavGraphRoutes

object RootNavigationGraphSpec : NavGraphSpec, CurrentHostScreenFlowHolder {

    override val nestedNavGraphs: List<NavGraphSpec> = listOf(
        HomeNavGraph,
        StatisticsNavGraph,
        ParametersNavGraph,
    )

    override val destinationsByRoute: Map<String, DestinationSpec<*>> = emptyMap()

    override val route: String = "root"
    override val startRoute: Route = HomeNavGraph

    override val currentGraphFlow: StateFlow<BottomNavGraphRoutes> by lazy { _currentGraphFlow }

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _currentGraphFlow = MutableStateFlow(
        value = startRoute.route.let(BottomNavGraphRoutes::findByRoute),
    )

    fun updateSelectedGraph(newGraph: NavGraphSpec) {
        scope.launch {
            val newRoute = newGraph.route.let(BottomNavGraphRoutes::findByRoute)
            _currentGraphFlow.emit(newRoute)
        }
    }
}
