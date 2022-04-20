package org.ireader.presentation.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import org.ireader.core.R
import org.ireader.core.utils.UiText
import org.ireader.core_api.source.HttpSource
import org.ireader.domain.ui.NavigationArgs
import org.ireader.presentation.feature_explore.presentation.browse.ExploreScreen
import org.ireader.presentation.feature_explore.presentation.browse.viewmodel.ExploreViewModel
import org.ireader.presentation.presentation.EmptyScreenComposable

object ExploreScreenSpec : ScreenSpec {

    override val navHostRoute: String = "explore_route/{sourceId}?query={query}"


    override val arguments: List<NamedNavArgument> = listOf(
        NavigationArgs.query,
        NavigationArgs.sourceId
    )


    fun buildRoute(sourceId: Long, query: String? = null): String {
        return if (query != null) {
            "explore_route/$sourceId?query=$query"
        } else {
            "explore_route/$sourceId"
        }
    }

    @OptIn(ExperimentalPagerApi::class, androidx.compose.animation.ExperimentalAnimationApi::class,
        androidx.compose.material.ExperimentalMaterialApi::class)
    @Composable
    override fun Content(
        navController: NavController,
        navBackStackEntry: NavBackStackEntry,
        scaffoldState: ScaffoldState,
    ) {
        val vm: ExploreViewModel = hiltViewModel()
        val focusManager = LocalFocusManager.current
        val source = vm.source
        val scope = rememberCoroutineScope()
        if (source != null) {
            ExploreScreen(
                navController = navController,
                vm = vm,
                onFilterClick = {
                    vm.toggleFilterMode()
                },
                source = source,
                onPop = { navController.popBackStack() },
                currentLayout = vm.layout,
                onLayoutTypeSelect = { layout ->
                    vm.saveLayoutType(layout)
                },
                onSearch = {
                    val query = vm.searchQuery
                    if (query != null && query.isNotBlank()) {
                        vm.searchQuery = query
                        vm.loadItems(true)
                    } else {
                        vm.stateListing = source.getListings().first()
                        vm.loadItems()
                        scope.launch {
                            vm.showSnackBar(UiText.StringResource(R.string.query_must_not_be_empty))
                        }
                    }
                    focusManager.clearFocus()
                },
                onSearchDisable = {
                    vm.toggleSearchMode(false)
                },
                onSearchEnable = {
                    vm.toggleSearchMode(true)
                },
                onValueChange = {
                    vm.searchQuery = it
                },
                onWebView = {
                    navController.navigate(WebViewScreenSpec.buildRoute(
                        url = (source as HttpSource).baseUrl
                    ))
                },
                getBooks = { query, listing, filters ->
                    vm.searchQuery = query
                    vm.stateListing = listing
                    vm.stateFilters = filters
                    vm.loadItems()
                },
                loadItems = { reset ->
                    vm.loadItems(reset)
                },
                onBook = { book ->
                    navController.navigate(
                        route = BookDetailScreenSpec.buildRoute(sourceId = book.sourceId,
                            bookId = book.id)
                    )
                },
                onAppbarWebView = {
                    navController.navigate(WebViewScreenSpec.buildRoute(url = it))
                }
            )
        } else {
            EmptyScreenComposable(navController, R.string.source_not_available)
        }
    }

}
