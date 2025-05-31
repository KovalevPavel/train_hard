package kovp.trainhard.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import kovp.trainhard.ui_theme.providers.themeColors
import kovp.trainhard.ui_theme.providers.themeTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    header: String?,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            onBackClick?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = themeColors.white,
                        contentDescription = null,
                    )
                }
            }
        },
        title = {
            header?.let {
                Text(
                    text = it,
                    style = themeTypography.header1,
                    color = themeColors.white,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        },
        actions = { actions?.let { it() } },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = themeColors.black),
    )
}
