package ir.kazemcodes.infinity.feature_settings.presentation.setting.extension_creator

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.zhuinden.simplestackcomposeintegration.core.LocalBackstack
import ir.kazemcodes.infinity.core.presentation.reusable_composable.NotImplementedText
import ir.kazemcodes.infinity.core.presentation.reusable_composable.TopAppBarBackButton
import ir.kazemcodes.infinity.core.presentation.reusable_composable.TopAppBarTitle

@Composable
fun ExtensionCreatorScreen() {
    val backStack = LocalBackstack.current
    Scaffold(topBar = {
        TopAppBar(
            title = {
                TopAppBarTitle("Extension Creator")
            },
            backgroundColor = MaterialTheme.colors.background,
            actions = {
            },
            navigationIcon = {
                TopAppBarBackButton(backStack = backStack)

            }
        )
    }) {
        NotImplementedText()
    }
}