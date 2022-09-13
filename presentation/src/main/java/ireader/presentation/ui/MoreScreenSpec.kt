package ireader.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource

import androidx.navigation.NamedNavArgument
import ireader.ui.component.Controller
import ireader.common.resources.discord
import ireader.ui.component.components.TitleToolbar
import ireader.presentation.ui.util.NavigationArgs
import ireader.presentation.R
import ireader.ui.settings.MainSettingScreenViewModel
import ireader.ui.settings.MoreScreen
import org.koin.androidx.compose.getViewModel

object MoreScreenSpec : BottomNavScreenSpec {
    override val icon: ImageVector = Icons.Filled.MoreHoriz
    override val label: Int = R.string.more
    override val navHostRoute: String = "more"

    override val arguments: List<NamedNavArgument> = listOf(
        NavigationArgs.showBottomNav
    )
    @ExperimentalMaterial3Api
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun TopBar(
        controller: Controller
    ) {
        TitleToolbar(
            title = stringResource(R.string.more),
            navController = null,
            scrollBehavior = controller.scrollBehavior
        )
    }

    @OptIn(
        ExperimentalMaterialApi::class,
        ExperimentalMaterial3Api::class
    )
    @Composable
    override fun Content(
        controller: Controller
    ) {
        val uriHandler = LocalUriHandler.current
        val vm: MainSettingScreenViewModel = getViewModel(owner = controller.navBackStackEntry)

        MoreScreen(
            modifier = Modifier.padding(controller.scaffoldPadding),
            vm = vm,
            onAbout = {
                controller.navController.navigate(AboutSettingSpec.navHostRoute)
            },
            onSettings = {
                controller.navController.navigate(SettingScreenSpec.navHostRoute)
            },
            onAppearanceScreen = {
                controller.navController.navigate(AppearanceScreenSpec.navHostRoute)
            },
            onBackupScreen = {
                controller.navController.navigate(BackupAndRestoreScreenSpec.navHostRoute)
            },
            onDownloadScreen = {
                controller.navController.navigate(DownloaderScreenSpec.navHostRoute)
            },
            onHelp = {
                uriHandler.openUri(discord)
            },
            onCategory = {
                controller.navController.navigate(CategoryScreenSpec.navHostRoute)
            }
        )
    }
}