package me.kovp.trainhard

import kotlinx.coroutines.flow.StateFlow
import me.kovp.trainhard.navigation_api.BottomNavGraphRoutes

interface CurrentHostScreenFlowHolder {
    val currentGraphFlow: StateFlow<BottomNavGraphRoutes>
}
