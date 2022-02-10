package org.ireader.domain.use_cases.fetchers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.ireader.core.utils.UiText
import org.ireader.core.utils.removeSameItemsFromList
import org.ireader.domain.R
import org.ireader.domain.models.entities.Book
import org.ireader.domain.models.entities.Chapter
import org.ireader.domain.models.source.Source
import org.ireader.domain.utils.Resource
import org.ireader.infinity.core.domain.use_cases.local.DeleteUseCase
import org.ireader.infinity.core.domain.use_cases.local.LocalInsertUseCases
import org.jsoup.Jsoup

class FetchBookDetailAndChapterDetailFromWebView {

    operator fun invoke(
        pageSource: String,
        localChapters: List<Chapter>? = null,
        localBook: Book? = null,
        source: Source,
        insertUseCases: LocalInsertUseCases,
        deleteUseCase: DeleteUseCase,
    ): Flow<Resource<UiText.DynamicString>> = flow {
        try {
            val bookFromPageSource = source.detailParse(Jsoup.parse(pageSource))
            val chaptersFromPageSource = source.chaptersParse(Jsoup.parse(pageSource))
            if (!chaptersFromPageSource.chapters.isNullOrEmpty()) {
                emit(Resource.Error<UiText.DynamicString>(UiText.StringResource(R.string.trying_to_fetch)))
                if (localChapters != null && chaptersFromPageSource.chapters.isNotEmpty() && localBook?.bookName?.isNotBlank() == true) {

                    val uniqueList = removeSameItemsFromList(oldList = localChapters,
                        newList = chaptersFromPageSource.chapters,
                        differentiateBy = {
                            it.title
                        })


                    deleteUseCase.deleteChaptersByBookId(bookId = localBook.id)
                    insertUseCases.insertChapters(uniqueList.map {
                        it.copy(
                            bookId = localBook.id,
                            bookName = localBook.bookName,
                            inLibrary = localBook.inLibrary,
                        )
                    })

                    emit(Resource.Success<UiText.DynamicString>(UiText.DynamicString("${bookFromPageSource.book.bookName} was fetched with ${chaptersFromPageSource.chapters.size}   chapters")))

                } else {
                    if (chaptersFromPageSource.chapters.isNotEmpty()) {
                        emit(Resource.Error<UiText.DynamicString>(UiText.StringResource(R.string.failed_to_get_content)))

                    }
                    if (localChapters == null) {
                        emit(Resource.Error<UiText.DynamicString>(UiText.StringResource(R.string.trying_in_few_second)))

                    }
                }

            } else {
                emit(Resource.Error(UiText.StringResource(R.string.failed_to_get_content)))

            }
        } catch (e: Exception) {
            emit(Resource.Error(UiText.StringResource(R.string.failed_to_get_content)))
        }
    }
}