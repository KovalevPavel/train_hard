package me.kovp.trainhard.navigation_api.navigation_styles

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.ramcosta.composedestinations.utils.navGraph
import me.kovp.trainhard.navigation_api.BottomNavGraphRoutes

abstract class TrainAnimatedStyle : DestinationStyle.Animated {
    internal abstract val enterAnimations: TrainScreenEnterAnim
    internal abstract val exitAnimations: TrainScreenExitAnim

    protected val defaultFiniteAnimationSpec: FiniteAnimationSpec<IntOffset> =
        tween(ANIMATION_DURATION_MS)
    protected val defaultFiniteFloatAnimationSpec: FiniteAnimationSpec<Float> =
        tween(ANIMATION_DURATION_MS)

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        val targetGraph = targetRouteGraph()
        val startGraph = startRouteGraph()

        return when {
            targetGraph == startGraph -> enterAnimations.onCurrentGraph()
            targetGraph > startGraph -> enterAnimations.onNextGraph()
            else -> enterAnimations.onPreviousGraph()
        }
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        val targetGraph = targetRouteGraph()
        val startGraph = startRouteGraph()

        return when {
            targetGraph == startGraph -> exitAnimations.onCurrentGraph()
            targetGraph > startGraph -> exitAnimations.onNextGraph()
            else -> exitAnimations.onPreviousGraph()
        }
    }

    private fun AnimatedContentTransitionScope<NavBackStackEntry>.startRouteGraph() =
        initialState.navGraph().route.let(BottomNavGraphRoutes::findByRoute)

    private fun AnimatedContentTransitionScope<NavBackStackEntry>.targetRouteGraph() =
        targetState.navGraph().route.let(BottomNavGraphRoutes::findByRoute)

    companion object {
        private const val ANIMATION_DURATION_MS = 250
    }
}
