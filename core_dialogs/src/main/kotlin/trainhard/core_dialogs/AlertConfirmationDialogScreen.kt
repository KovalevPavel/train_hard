package trainhard.core_dialogs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import me.kovp.trainhard.navigation_api.Screen

@Parcelize
data class AlertConfirmationDialogScreen(
    val dialogLabel: String,
    val title: String = "",
    val message: String = "",
    val positiveAction: String,
    val negativeAction: String = "",
) : Screen, Parcelable
