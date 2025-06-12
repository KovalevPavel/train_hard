package kovp.trainhard.composeApp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import kovp.trainhard.composeApp.bottom_navigation.BottomBar
import kovp.trainhard.ui_theme.TrainHardTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun BottomBarPreview() {
    TrainHardTheme {
        BottomBar(navController = rememberNavController())
    }
}
