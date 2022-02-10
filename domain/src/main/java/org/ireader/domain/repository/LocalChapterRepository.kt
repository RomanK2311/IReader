package org.ireader.domain.repository

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.ireader.domain.models.entities.Chapter

interface LocalChapterRepository {


    fun getOneChapterById(
        chapterId: Int,
    ): Flow<Chapter?>

    fun getChaptersByBookId(
        bookId: Int,
        isAsc: Boolean = true,
    ): Flow<List<Chapter>?>


    fun getLastReadChapter(bookId: Int): Flow<Chapter?>


    suspend fun insertChapter(chapter: Chapter)

    suspend fun insertChapters(
        chapters: List<Chapter>,
    )


    fun getLocalChaptersByPaging(
        bookId: Int, isAsc: Boolean,
    ): PagingSource<Int, Chapter>


    suspend fun deleteChaptersByBookId(
        bookId: Int,
    )

    suspend fun deleteChapters(chapters: List<Chapter>)

    suspend fun deleteChapterByChapter(
        chapter: Chapter,
    )

    suspend fun deleteNotInLibraryChapters()

    suspend fun deleteAllChapters()


}