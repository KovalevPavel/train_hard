package kovp.trainhard.app

import kotlinx.coroutines.flow.StateFlow
import kovp.trainhard.navigation_api.BottomNavGraphRoutes

interface CurrentHostScreenFlowHolder {
    val currentGraphFlow: StateFlow<BottomNavGraphRoutes>
}
