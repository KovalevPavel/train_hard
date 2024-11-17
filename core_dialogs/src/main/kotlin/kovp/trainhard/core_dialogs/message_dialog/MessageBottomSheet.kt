package kovp.trainhard.core_dialogs.message_dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kovp.trainhard.core_dialogs.DialogButton
import kovp.trainhard.core_dialogs.DialogState
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@Composable
internal fun MessageBottomSheet(
    state: MessageDialogState,
    onPositive: () -> Unit,
    onNegative: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DialogTexts(title = state.title, message = state.message)
        Spacer(modifier = Modifier.height(16.dp))
        DialogButtons(
            positive = state.positiveAction,
            negative = state.negativeAction,
            onPositive = onPositive,
            onNegative = onNegative,
        )
    }
}

@Composable
private fun DialogTexts(
    title: String,
    message: String,
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        textAlign = TextAlign.Center,
        style = themeTypography.header1,
        color = themeColors.white,
    )

    if (message.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            textAlign = TextAlign.Center,
            style = themeTypography.body2,
            color = themeColors.white,
        )
    }
}

@Composable
private fun DialogButtons(
    positive: DialogState.Action,
    negative: DialogState.Action?,
    onPositive: () -> Unit,
    onNegative: () -> Unit,
) {
    DialogButton(
        action = positive,
        isPrimary = true,
        onClick = onPositive,
    )

    negative?.let {
        DialogButton(
            action = it,
            isPrimary = false,
            onClick = onNegative,
        )
    }
}
