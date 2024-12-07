package kovp.trainhard.components

import androidx.compose.ui.Modifier

fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier = { this },
    ifFalse: Modifier.() -> Modifier = { this },
): Modifier = if (condition) {
    Modifier.then(ifTrue())
} else {
    ifFalse()
}
