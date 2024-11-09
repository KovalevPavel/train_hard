package kovp.trainhard.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
inline fun <reified State : Any> StateContainer(
    state: State,
    noinline content: @Composable (State) -> Unit,
) {
    AnimatedContent(
        modifier = Modifier.fillMaxSize(),
        targetState = state,
        label = State::class.java.toString(),
        contentKey = { it::class.java },
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) {
        content(it)
    }
}
