package kovp.trainhard.navigation_api.navigation_styles

import androidx.compose.animation.EnterTransition

internal data class TrainScreenEnterAnim(
    /**
     * Навигация в пределах одного графа
     */
    val onCurrentGraph: () -> EnterTransition? = { null },
    /**
     * Навигация на следующую вкладку BottomBar'а
     */
    val onNextGraph: () -> EnterTransition? = { null },
    /**
     * Навигация на предыдущую вкладку BottomBar'а
     */
    val onPreviousGraph: () -> EnterTransition? = { null },
)
