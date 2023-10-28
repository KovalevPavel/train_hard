package kovp.trainhard.navigation_api.navigation_styles

import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object BottomNavigationTransition : TrainAnimatedStyle() {

    override val enterAnimations: TrainScreenEnterAnim = TrainScreenEnterAnim(
        onNextGraph = {
            slideInHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> offset }
        },
        onPreviousGraph = {
            slideInHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> -offset }
        },
    )

    override val exitAnimations: TrainScreenExitAnim = TrainScreenExitAnim(
        onCurrentGraph = {
            fadeOut(defaultFiniteFloatAnimationSpec)
        },
        onNextGraph = {
            slideOutHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> -offset }
        },
        onPreviousGraph = {
            slideOutHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> offset }
        },
    )
}
