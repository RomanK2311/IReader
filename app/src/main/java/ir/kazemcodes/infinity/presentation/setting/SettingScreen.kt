package ir.kazemcodes.infinity.setting_feature.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Extension
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zhuinden.simplestackcomposeintegration.core.LocalBackstack
import ir.kazemcodes.infinity.base_feature.navigation.DownloadScreenKey
import ir.kazemcodes.infinity.base_feature.navigation.ExtensionCreatorScreenKey
import ir.kazemcodes.infinity.presentation.book_detail.Constants
import ir.kazemcodes.infinity.presentation.home.ComposeKey

@Composable
fun SettingScreen(modifier: Modifier = Modifier) {
    val settingItems = listOf<SettingItems>(
        SettingItems.Downloads,
        SettingItems.ExtensionCreator,
    )
    Box(modifier.fillMaxSize()) {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Setting",
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
                elevation = Constants.DEFAULT_ELEVATION,
            )
        }) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
            ) {

                settingItems.forEach { item ->
                    SettingsItem(title = item.title, imageVector = item.icon, DestinationScreenKey = item.DestinationScreenKey)
                }

            }

        }
    }

}

sealed class SettingItems(val title: String, val icon: ImageVector,val DestinationScreenKey : ComposeKey) {
    object Downloads : SettingItems("Downloads", Icons.Default.Download, DownloadScreenKey())
    object ExtensionCreator : SettingItems("ExtensionCreator", Icons.Default.Extension, ExtensionCreatorScreenKey())
}

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    imageVector: ImageVector,
    DestinationScreenKey : ComposeKey,
) {
    val backstack = LocalBackstack.current
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp)
            .clickable(interactionSource = interactionSource,
                indication = null) { backstack.goTo(DestinationScreenKey)},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = imageVector, contentDescription = "$title icon")
        Spacer(modifier = modifier.width(20.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )


    }

}