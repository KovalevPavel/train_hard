package me.kovp.trainhard.navigation_api.navigation_styles

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object SlideFromRightTransition : TrainAnimatedStyle() {
    override val enterAnimations: TrainScreenEnterAnim = TrainScreenEnterAnim(
        onCurrentGraph = {
            slideInHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> offset }
        },
        onNextGraph = {
            slideInHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> offset }
        },
        onPreviousGraph = {
            slideInHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> -offset }
        }
    )

    override val exitAnimations: TrainScreenExitAnim = TrainScreenExitAnim(
        onCurrentGraph = {
            slideOutHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> offset }
        },
        onNextGraph = {
            slideOutHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> -offset }
        },
        onPreviousGraph = {
            slideOutHorizontally(animationSpec = defaultFiniteAnimationSpec) { offset -> offset }
        },
    )
}
