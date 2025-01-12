package com.example.bookdbapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert
    suspend fun insertBook(book: Book): Long

    @Insert
    suspend fun insertAuthor(author: Author): Long

    @Insert
    suspend fun insertBookAuthorCrossRef(crossRef: BookAuthorCrossRef)

    @Transaction
    @Query("""
        SELECT b.* 
        FROM books AS b
        INNER JOIN BookAuthorCrossRef AS bac ON b.id = bac.bookId
        INNER JOIN authors AS a ON bac.authorId = a.id
        WHERE a.name LIKE '%' || :filter || '%'
    """)
    fun getBooksWithAuthorsByAuthor(filter: String): Flow<List<BookWithAuthors>>

    @Transaction
    @Query("""
        SELECT b.* 
        FROM books AS b
        INNER JOIN BookAuthorCrossRef AS bac ON b.id = bac.bookId
        INNER JOIN authors AS a ON bac.authorId = a.id
        WHERE LOWER(b.title) LIKE '%' || LOWER(:filter) || '%'
    """)
    fun getBooksWithAuthorsByTitle(filter: String): Flow<List<BookWithAuthors>>

    @Transaction
    @Query("""
        SELECT DISTINCT b.* 
        FROM books AS b
        INNER JOIN BookAuthorCrossRef AS bac ON b.id = bac.bookId
        INNER JOIN authors AS a ON bac.authorId = a.id
        WHERE b.title LIKE '%' || :filter || '%' OR a.name LIKE '%' || :filter || '%'
    """)
    fun searchBooks(filter: String): Flow<List<BookWithAuthors>>

    @Transaction
    @Query("SELECT * FROM books")
    fun getBooksWithAuthors(): Flow<List<BookWithAuthors>>

    @Transaction
    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getBookWithAuthors(bookId: Long): Flow<List<BookWithAuthors>>

}