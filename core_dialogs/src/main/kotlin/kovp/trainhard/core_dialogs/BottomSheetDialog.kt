package kovp.trainhard.core_dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kovp.trainhard.components.button.TrainButton
import kovp.trainhard.core_dialogs.message_dialog.MessageBottomSheet
import kovp.trainhard.core_dialogs.message_dialog.MessageDialogState
import kovp.trainhard.ui_theme.TrainHardTheme
import kovp.trainhard.ui_theme.providers.themeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(
    state: DialogState?,
    onPositiveClick: (Any?) -> Unit,
    onNegativeClick: (Any?) -> Unit,
    onCancel: () -> Unit,
) {
    val modalState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onCancel,
        sheetState = modalState,
        containerColor = themeColors.black,
    ) {
        when (state) {
            is MessageDialogState -> {
                MessageBottomSheet(
                    state = state,
                    onPositive = {
                        scope.launch { modalState.hide() }
                            .invokeOnCompletion {
                                if (!modalState.isVisible) {
                                    onPositiveClick(null)
                                }
                            }
                    },
                    onNegative = {
                        scope.launch { modalState.hide() }
                            .invokeOnCompletion {
                                if (!modalState.isVisible) {
                                    onNegativeClick(null)
                                }
                            }
                    },
                )
            }
        }
    }
}

@Composable
internal fun DialogButton(
    action: DialogState.Action,
    isPrimary: Boolean,
    onClick: () -> Unit,
) {
    TrainButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        isPrimary = isPrimary,
        isEnabled = true,
        label = action.action,
        onClick = onClick,
    )
}

@Preview
@Composable
private fun BottomSheetDialogPreview(
    @PreviewParameter(BottomSheetPreviewProvider::class) state: DialogState,
) {
    TrainHardTheme {
        BottomSheetDialog(
            state = state,
            onPositiveClick = {},
            onNegativeClick = {},
            onCancel = {},
        )
    }
}

private class BottomSheetPreviewProvider : PreviewParameterProvider<DialogState> {
    override val values: Sequence<DialogState>
        get() = sequenceOf(
            MessageDialogState(
                dialogId = "",
                title = "Жим лежа",
                message = "Удалить упражнение?",
                positiveAction = DialogState.Action(action = "Удалить"),
                negativeAction = DialogState.Action(action = "Отмена"),
            ),
            MessageDialogState(
                dialogId = "",
                title = "Жим лежа".repeat(10),
                message = "Удалить упражнение?".repeat(10),
                positiveAction = DialogState.Action(
                    action = "Удалить".repeat(10),
                ),
                negativeAction = DialogState.Action(
                    action = "Отмена".repeat(10),
                ),
            ),
            MessageDialogState(
                dialogId = "",
                title = "Произошла ошибка",
                positiveAction = DialogState.Action(
                    action = "Удалить",
                ),
            ),
        )
}
