package me.kovp.trainhard.navigation_api.navigation_styles

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle
import me.kovp.trainhard.navigation_api.BottomNavGraphRoutes

object BottomNavigationTransition : DestinationStyle.Animated {
    private const val ANIMATION_DURATION_MS = 250

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        val targetRoute = targetRoute()
        val startRoute = startRoute()

        return when {
            // навигация в пределах одного графа
            targetRoute == startRoute -> {
                null
            }
            // навигация на правую вкладку
            targetRoute > startRoute -> {
                slideInHorizontally(
                    initialOffsetX = { offset -> offset },
                    animationSpec = tween(ANIMATION_DURATION_MS)
                )
            }
            // навигация на левую вкладку
            else -> {
                slideInHorizontally(
                    initialOffsetX = { offset -> -offset },
                    animationSpec = tween(ANIMATION_DURATION_MS)
                )
            }
        }
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition? {
        val targetRoute = targetRoute()
        val startRoute = startRoute()

        return when {
            // навигация в пределах одного графа
            targetRoute == startRoute -> {
                null
            }
            // навигация на правую вкладку
            targetRoute > startRoute -> {
                slideOutHorizontally(
                    targetOffsetX = { offset -> -offset },
                    animationSpec = tween(ANIMATION_DURATION_MS)
                )
            }
            // навигация на левую вкладку
            else -> {
                slideOutHorizontally(
                    targetOffsetX = { offset -> offset },
                    animationSpec = tween(ANIMATION_DURATION_MS)
                )
            }
        }
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? =
        null

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition? =
        null

    private fun AnimatedContentTransitionScope<NavBackStackEntry>.startRoute() =
        initialState.destination.parent?.route.let(BottomNavGraphRoutes::findByRoute)

    private fun AnimatedContentTransitionScope<NavBackStackEntry>.targetRoute() =
        targetState.destination.parent?.route.let(BottomNavGraphRoutes::findByRoute)
}
