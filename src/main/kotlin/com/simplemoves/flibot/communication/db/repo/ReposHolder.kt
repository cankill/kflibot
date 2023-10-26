package com.simplemoves.flibot.communication.db.repo

import com.simplemoves.flibot.communication.db.model.BookModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

class ReposHolder {
    private val seriesRepo = SeriesRepo()
    private val authorRepo = AuthorRepo()
    private val bookRepo = BookRepo()

    fun search(query: String): List<BookModel> = runBlocking {
        val seriesBooks = async { seriesRepo.findSeries(query) }
        val authorBooks = async { authorRepo.findAuthor(query) }
        val books = async { bookRepo.findBooks(query) }

        listOf(seriesBooks, authorBooks, books)
            .awaitAll()
            .flatten()
    }
}