package ireader.domain.data.repository

import kotlinx.coroutines.flow.Flow
import ireader.common.models.entities.BookCategory

interface BookCategoryRepository {
    fun subscribeAll(): Flow<List<BookCategory>>

    suspend fun findAll(): List<BookCategory>

    suspend fun insert(category: BookCategory)
    suspend fun insertAll(categories: List<BookCategory>)
    suspend fun delete(category: BookCategory)
    suspend fun delete(bookId: Long)
    suspend fun deleteAll(category: List<BookCategory>)
}