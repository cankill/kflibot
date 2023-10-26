package com.simplemoves.flibot.communication.db.model

import com.simplemoves.flibot.communication.db.table.SeriesNameTable
import com.simplemoves.flibot.communication.db.table.SeriesTable
import com.simplemoves.flibot.utils.escapeMarkdown
import org.jetbrains.exposed.sql.ResultRow

data class SeriesModel (
    val name: String,
    val number: UInt
) {
    companion object {
        fun fromRow(resultRow: ResultRow): SeriesModel? {
            val name: String? = resultRow[SeriesNameTable.seriesName]?.escapeMarkdown()
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

    fun toTelegramMessage(): String = """_${name}_ \($number\)"""
}
