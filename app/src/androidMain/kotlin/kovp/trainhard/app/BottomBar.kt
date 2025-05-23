package kovp.trainhard.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import kovp.trainhard.app.bottom_navigation.BottomBar
import kovp.trainhard.ui_theme.TrainHardTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun BottomBarPreview() {
    TrainHardTheme {
        BottomBar(navController = rememberNavController())
    }
}
