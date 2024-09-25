package com.sanmer.mrepo.ui.screens.settings.app.items

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanmer.mrepo.R
import com.sanmer.mrepo.datastore.DarkMode
import com.sanmer.mrepo.ui.component.NavigationBarsSpacer
import com.sanmer.mrepo.ui.component.SettingNormalItem
import com.sanmer.mrepo.ui.utils.expandedShape

@Composable
fun AppThemeItem(
    themeColor: Int,
    darkMode: DarkMode,
    isDarkMode: Boolean,
    onThemeColorChange: (Int) -> Unit,
    onDarkModeChange: (DarkMode) -> Unit
) {
    var open by rememberSaveable { mutableStateOf(false) }

    SettingNormalItem(
        icon = R.drawable.color_swatch,
        title = stringResource(id = R.string.settings_app_theme),
        desc = stringResource(id = R.string.settings_app_theme_desc),
        onClick = { open = true }
    )

    if (open) {
        BottomSheet(
            onClose = { open = false },
            themeColor = themeColor,
            darkMode = darkMode,
            isDarkMode = isDarkMode,
            onThemeColorChange =onThemeColorChange,
            onDarkModeChange = onDarkModeChange
        )
    }
}

@Composable
private fun BottomSheet(
    onClose: () -> Unit,
    themeColor: Int,
    darkMode: DarkMode,
    isDarkMode: Boolean,
    onThemeColorChange: (Int) -> Unit,
    onDarkModeChange: (DarkMode) -> Unit,
) = ModalBottomSheet(
    onDismissRequest = onClose,
    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    shape = BottomSheetDefaults.expandedShape(15.dp),
    windowInsets = WindowInsets(0)
) {
    Text(
        text = stringResource(id = R.string.settings_app_theme),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )

    TitleItem(text = stringResource(id = R.string.app_theme_palette))
    ThemePaletteItem(
        themeColor = themeColor,
        isDarkMode = isDarkMode,
        onChange = onThemeColorChange
    )

    TitleItem(text = stringResource(id = R.string.app_theme_dark_theme))
    DarkModeItem(
        darkMode = darkMode,
        onChange = onDarkModeChange
    )

    NavigationBarsSpacer()
}

@Composable
private fun TitleItem(
    text: String
) = Text(
    text = text,
    style = MaterialTheme.typography.titleSmall,
    modifier = Modifier.padding(start = 18.dp, top = 18.dp)
)