package ir.kazemcodes.infinity.base_feature.navigation

import android.content.Context
import android.view.Window
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackcomposeintegration.services.rememberService
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.lookup
import ir.kazemcodes.infinity.domain.models.remote.Book
import ir.kazemcodes.infinity.domain.models.remote.Chapter
import ir.kazemcodes.infinity.domain.use_cases.preferences.PreferencesUseCase
import ir.kazemcodes.infinity.domain.use_cases.local.LocalUseCase
import ir.kazemcodes.infinity.domain.use_cases.remote.RemoteUseCase
import ir.kazemcodes.infinity.presentation.book_detail.BookDetailScreen
import ir.kazemcodes.infinity.presentation.book_detail.BookDetailViewModel
import ir.kazemcodes.infinity.presentation.browse.BrowseViewModel
import ir.kazemcodes.infinity.presentation.browse.BrowserScreen
import ir.kazemcodes.infinity.presentation.chapter_detail.ChapterDetailScreen
import ir.kazemcodes.infinity.presentation.chapter_detail.ChapterDetailViewModel
import ir.kazemcodes.infinity.presentation.extension.ExtensionScreen
import ir.kazemcodes.infinity.presentation.home.ComposeKey
import ir.kazemcodes.infinity.presentation.home.MainScreen
import ir.kazemcodes.infinity.presentation.home.MainViewModel
import ir.kazemcodes.infinity.presentation.library.LibraryViewModel
import ir.kazemcodes.infinity.presentation.reader.ReaderScreenViewModel
import ir.kazemcodes.infinity.presentation.reader.ReadingScreen
import ir.kazemcodes.infinity.presentation.setting.SettingViewModel
import ir.kazemcodes.infinity.presentation.setting.dns.DnsOverHttpScreen
import ir.kazemcodes.infinity.presentation.setting.downloader.DownloaderScreen
import ir.kazemcodes.infinity.presentation.setting.extension_creator.ExtensionCreatorScreen
import ir.kazemcodes.infinity.presentation.webview.WebPageScreen
import ir.kazemcodes.infinity.util.mappingApiNameToAPi
import kotlinx.parcelize.Parcelize


@Immutable
@Parcelize
data class MainScreenKey(val noArgument: String = "") : ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier)  {
        MainScreen()
    }

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(LibraryViewModel(lookup<LocalUseCase>(), lookup<PreferencesUseCase>()))
            add(MainViewModel())
        }
    }
}


@Immutable
@Parcelize
data class BrowserScreenKey(val sourceName: String, val isLatestUpdateMode: Boolean = true) :
    ComposeKey() {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        BrowserScreen()
    }

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(BrowseViewModel(lookup<LocalUseCase>(),
                lookup<RemoteUseCase>(),
                preferencesUseCase = lookup<PreferencesUseCase>(),
                source = mappingApiNameToAPi(sourceName,lookup<Context>()),
                isLatestUpdateMode = isLatestUpdateMode),)
        }

    }

}

@Immutable
@Parcelize
data class BookDetailKey(val book: Book, val sourceName: String) : ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        val viewModel = rememberService<BookDetailViewModel>()

        BookDetailScreen(book = book, viewModel = viewModel)

    }

    override fun bindServices(serviceBinder: ServiceBinder) {

        with(serviceBinder) {
            add(BookDetailViewModel(lookup<LocalUseCase>(),
                lookup<RemoteUseCase>(),
                source = mappingApiNameToAPi(sourceName,lookup<Context>()),
                book = book,
                lookup<PreferencesUseCase>()))
        }
    }
}

@Immutable
@Parcelize
data class WebViewKey(val url: String) : ComposeKey() {

    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        WebPageScreen(url)
    }
}

@Immutable
@Parcelize
data class ChapterDetailKey(
    val book: Book,
    val chapters: List<Chapter>,
    val sourceName: String,
) : ComposeKey() {

    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        ChapterDetailScreen(chapters = chapters, book = book)
    }

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(ChapterDetailViewModel(lookup<LocalUseCase>(),
                source = mappingApiNameToAPi(sourceName,lookup<Context>()),
                book = book))
        }
    }
}

@Immutable
@Parcelize
data class ReaderScreenKey(
    val book: Book,
    val chapter: Chapter,
    val sourceName: String,
    val chapters: List<Chapter>,
) : ComposeKey() {

    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        ReadingScreen(book = book, chapter = chapter)
    }

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(ReaderScreenViewModel(
                localUseCase = lookup<LocalUseCase>(),
                remoteUseCase = lookup<RemoteUseCase>(),
                preferencesUseCase = lookup<PreferencesUseCase>(),
                source = mappingApiNameToAPi(sourceName,context = lookup<Context>()),
                book = book,
                chapter = chapter,
                chapters = chapters,
                window = lookup<Window>()
            ))
        }
    }
}

@Immutable
@Parcelize
data class ExtensionScreenKey(val noArgs: String = "") : ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        ExtensionScreen()
    }
}
@Immutable
@Parcelize
data class DownloadScreenKey(val noArgs: String = "") : ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        DownloaderScreen()
    }
}
@Immutable
@Parcelize
data class ExtensionCreatorScreenKey(val noArgs: String = "") : ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        ExtensionCreatorScreen()
    }
}
@Immutable
@Parcelize
data class DnsOverHttpScreenKey(val noArgs: String = "") : ComposeKey() {
    @Composable
    override fun ScreenComposable(modifier: Modifier) {
        val viewModel = rememberService<SettingViewModel>()
        DnsOverHttpScreen(viewModel)
    }
    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(SettingViewModel(
                preferencesUseCase = lookup<PreferencesUseCase>(),
            ))
        }
    }
}