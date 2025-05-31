package kovp.trainhard.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors

@Preview(name = "TopBar")
@Composable
private fun TopBarPreview(
    @PreviewParameter(TopBarPreviewProvider::class) state: TopBarState?,
) {
    TrainHardTheme {
        TopBar(
            header = state?.title,
            onBackClick = state?.withBackClick?.takeIf { it }?.let { {} },
            actions = state?.actions?.let { list ->
                {
                    list.forEach { ac ->
                        TextButton(onClick = {}) {
                            Text(text = ac, color = themeColors.lime)
                        }
                    }
                }
            },
        )
    }
}

private class TopBarPreviewProvider : PreviewParameterProvider<TopBarState?> {
    override val values: Sequence<TopBarState?>
        get() = sequenceOf(
            null,
            TopBarState(
                title = TITLE.trim(),
                withBackClick = false,
                actions = null,
            ),
            TopBarState(
                title = null,
                withBackClick = true,
                actions = null,
            ),
            TopBarState(
                title = TITLE.repeat(4).trim(),
                withBackClick = false,
                actions = null,
            ),
            TopBarState(
                title = TITLE.repeat(4).trim(),
                withBackClick = false,
                actions = listOf("action"),
            ),
            TopBarState(
                title = TITLE.repeat(4).trim(),
                withBackClick = false,
                actions = listOf("action0", "action1"),
            ),
            TopBarState(
                title = TITLE.trim(),
                withBackClick = true,
                actions = null,
            ),
            TopBarState(
                title = TITLE.repeat(4).trim(),
                withBackClick = true,
                actions = null,
            ),
            TopBarState(
                title = TITLE.repeat(4).trim(),
                withBackClick = true,
                actions = listOf("action"),
            ),
            TopBarState(
                title = TITLE.repeat(4).trim(),
                withBackClick = true,
                actions = listOf("action0", "action1"),
            ),
        )

    companion object {
        private const val TITLE = "Заголовок "
    }
}

private class TopBarState(
    val title: String?,
    val withBackClick: Boolean,
    val actions: List<String>?,
)
