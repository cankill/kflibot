package com.simplemoves.flibot.communication.db.model

import com.simplemoves.flibot.communication.db.table.*
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
            title = resultRow[BookTable.title],
            size = resultRow[BookTable.fileSize],
            type = resultRow[BookTable.fileType],
            language = resultRow[BookTable.language],
            author = AuthorModel.fromRow(resultRow),
            series = SeriesModel.fromRow(resultRow),
            rate = rating
        )
    }
}

data class AuthorModel (
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val nickName: String,
) {
    companion object {
        fun fromRow(resultRow: ResultRow) = AuthorModel(
            firstName = resultRow[AuthorNameTable.firstName],
            middleName = resultRow[AuthorNameTable.middleName],
            lastName = resultRow[AuthorNameTable.lastName],
            nickName = resultRow[AuthorNameTable.nickName]
        )
    }
}

data class SeriesModel (
    val name: String,
    val number: UInt
) {
    companion object {
        fun fromRow(resultRow: ResultRow): SeriesModel? {
            val name: String? = resultRow[SeriesNameTable.seriesName]
            val number: UInt? = resultRow[SeriesTable.seriesNumber]
            if (name == null || number == null) {
                return null
            }

            return SeriesModel(
                name,
                number
            )
        }
    }
}