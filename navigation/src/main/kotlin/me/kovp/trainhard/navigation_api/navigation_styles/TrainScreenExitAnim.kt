package me.kovp.trainhard.navigation_api.navigation_styles

import androidx.compose.animation.ExitTransition

internal data class TrainScreenExitAnim(
    /**
     * Навигация в пределах одного графа
     */
    val onCurrentGraph: () -> ExitTransition? = { null },
    /**
     * Навигация на следующую вкладку BottomBar'а
     */
    val onNextGraph: () -> ExitTransition? = { null },
    /**
     * Навигация на предыдущую вкладку BottomBar'а
     */
    val onPreviousGraph: () -> ExitTransition? = { null },
)