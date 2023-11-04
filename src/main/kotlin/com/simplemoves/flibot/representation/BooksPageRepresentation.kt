package com.simplemoves.flibot.representation

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.simplemoves.flibot.communication.db.model.BookModel
import kotlin.math.ceil
import kotlin.math.min

class BooksPageRepresentation(private val booksPerPage: Int) {
    fun getPage(currentPageNumber: Int, queryKey: String, books: List<BookModel>): Pair<String, InlineKeyboardMarkup?> {
        if (books.isEmpty()) return "Ничего не найдено" to null

        val maxPage = ceil(books.size.toFloat() / booksPerPage).toInt() - 1
        val chosenPageNumber = min(currentPageNumber, maxPage)
        val startIndex = chosenPageNumber * booksPerPage
        val endIndex = min(startIndex + booksPerPage, books.size) - 1

        val  foundBooks = (startIndex .. endIndex)
            .map { index ->
                val book = books[index]
                book.toTelegramMessage(queryKey, index.toString())
            }


        val inlineKeyboardMarkup = if (maxPage > 1) {
            InlineKeyboardMarkup.createSingleRowKeyboard(
                (0 .. maxPage).map { pageNumber ->
                    InlineKeyboardButton.CallbackData(
                        text = formatPageNumber(pageNumber, currentPageNumber),
                        callbackData = "/query/$queryKey/page/$pageNumber")
                }
            )
        } else null

        val page = foundBooks.joinToString("\n\n")

        return page to inlineKeyboardMarkup
    }

    private fun formatPageNumber(pageNumber: Int, currentPageNumber: Int): String {
        val printPageNumber = (pageNumber + 1).toString()
        return if (pageNumber == currentPageNumber) "-$printPageNumber-" else printPageNumber
    }
}