package com.simplemoves.flibot.representation

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.simplemoves.flibot.communication.db.model.BookModel

class BookDetailsRepresentation {
    fun getPage(book: BookModel): Pair<String, InlineKeyboardMarkup?> {
        val bookUrl = "/b/${book.id}"
        val inlineKeyboardMarkup = InlineKeyboardMarkup.createSingleRowKeyboard(
            InlineKeyboardButton.CallbackData(text = "fb2", callbackData = "$bookUrl/fb2"),
            InlineKeyboardButton.CallbackData(text = "epub", callbackData = "$bookUrl/epub"),
            InlineKeyboardButton.CallbackData(text = "mobi", callbackData = "$bookUrl/mobi"))

        val page = book.toTelegramExtendedMessage()

        return page to inlineKeyboardMarkup
    }
}