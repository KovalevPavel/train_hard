package kovp.trainhard.navigation_api.navigation_styles

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

object SlideFromBottomTransition : TrainAnimatedStyle() {
    override val enterAnimations: TrainScreenEnterAnim = TrainScreenEnterAnim(
        onCurrentGraph = {
            slideInVertically(animationSpec = defaultFiniteAnimationSpec) { offset -> offset }
        },
        onNextGraph = {
            slideInHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> offset }
        },
        onPreviousGraph = {
            slideInHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> -offset }
        },
    )

    override val exitAnimations: TrainScreenExitAnim = TrainScreenExitAnim(
        onCurrentGraph = {
            slideOutVertically(animationSpec = defaultFiniteAnimationSpec) { offset -> offset }
        },
        onNextGraph = {
            slideOutHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> -offset }
        },
        onPreviousGraph = {
            slideOutHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> offset }
        },
    )

    override val popExitAnimations: TrainScreenExitAnim = exitAnimations
}
