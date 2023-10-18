package com.simplemoves.flibot.communication.db.repo

import com.simplemoves.flibot.communication.db.model.BookModel
import com.simplemoves.flibot.communication.db.table.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class AuthorRepo {
    suspend fun findAuthor(query: String): List<BookModel> {
        val lowerCaseQuery = query.lowercase()
        return newSuspendedTransaction(Dispatchers.IO) {
            repetitionAttempts = 1

            val res = AuthorNameTable
                .join(AuthorTable, JoinType.LEFT, AuthorNameTable.id, AuthorTable.authorId)
                .join(BookTable, JoinType.LEFT, AuthorTable.bookId, BookTable.id)
                .join(SeriesTable, JoinType.LEFT, AuthorTable.bookId, SeriesTable.bookId)
                .join(SeriesNameTable, JoinType.LEFT, SeriesTable.seriesId, SeriesNameTable.id)
                .slice(
                    BookTable.id,
                    BookTable.title,
                    BookTable.fileSize,
                    BookTable.fileType,
                    BookTable.language,
                    AuthorNameTable.firstName,
                    AuthorNameTable.middleName,
                    AuthorNameTable.lastName,
                    AuthorNameTable.nickName,
                    SeriesNameTable.seriesName,
                    SeriesTable.seriesNumber,
                )
                .select((
                        (AuthorNameTable.firstName.lowerCase() like "%$lowerCaseQuery%") or
                        (AuthorNameTable.middleName.lowerCase() like "%$lowerCaseQuery%") or
                        (AuthorNameTable.lastName.lowerCase() like "%$lowerCaseQuery%") or
                        (AuthorNameTable.nickName.lowerCase() like "%$lowerCaseQuery%")) and
                        (BookTable.deleted eq '0'))
                .orderBy(
                    AuthorNameTable.lastName to SortOrder.ASC,
                    SeriesNameTable.seriesName to SortOrder.ASC,
                    SeriesTable.seriesNumber to SortOrder.ASC)
                .map { bookJoinRow ->
                    val bookId = bookJoinRow[BookTable.id]

                    val rating = RateTable
                        .slice(RateTable.rate.sum(), RateTable.rate.count())
                        .select(RateTable.bookId eq bookId)
                        .map {
                            val rate = it[RateTable.rate.sum()]?.toDouble()
                            val count = it[RateTable.rate.count()].toDouble()
                            rate?.run { this / count }
                        }.firstOrNull()

                    BookModel.fromRow(bookJoinRow, rating)
                }


            res
        }
    }
}