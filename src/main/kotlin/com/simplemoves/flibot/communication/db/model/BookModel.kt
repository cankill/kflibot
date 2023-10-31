package com.simplemoves.flibot.communication.db.model

import com.simplemoves.flibot.communication.db.table.*
import com.simplemoves.flibot.utils.escapeMarkdown
import org.jetbrains.exposed.sql.ResultRow

data class BookModel (
    val id: Int,
    val title: String,
    val size: UInt,
    val type: String,
    val language: String,
    val author: AuthorModel,
    val series: SeriesModel?,
    var rate: Double? = null
) {
    companion object {
        fun fromRow(resultRow: ResultRow, rating: Double?) = BookModel(
            id = resultRow[BookTable.id].value,
            title = resultRow[BookTable.title].escapeMarkdown(),
            size = resultRow[BookTable.fileSize],
            type = resultRow[BookTable.fileType].escapeMarkdown(),
            language = resultRow[BookTable.language].escapeMarkdown(),
            author = AuthorModel.fromRow(resultRow),
            series = SeriesModel.fromRow(resultRow),
            rate = rating
        )
    }

    fun toTelegramMessage(queryKey: String, bookIndex: String): String {
        val seriesName = series?.toTelegramMessage()?.run { "\n$this" } ?: ""
        val author = author.toTelegramMessage()
        val link = "\n"+"""Бочка рома: /b${queryKey}i$bookIndex"""
        return """*$title* — $language$seriesName$author$link"""
    }

    fun toTelegramExtendedMessage(): String {
        val seriesName = series?.toTelegramMessage()?.run { "\n$this" } ?: ""
        val author = author.toTelegramMessage()
//        val link = "\n"+"""Загрузить бочку рома: /barrel${key}of$index"""
        val size = "\nSize: ${size/1024u} [KB]"
        return """*$title* — $language$seriesName$author$size"""
    }
}
