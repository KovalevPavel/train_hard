import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kovp.trainhard.core_dialogs.BottomSheetDialog
import kovp.trainhard.core_dialogs.DialogState
import kovp.trainhard.core_dialogs.message_dialog.MessageDialogState
import kovp.trainhard.ui_theme.TrainHardTheme

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
