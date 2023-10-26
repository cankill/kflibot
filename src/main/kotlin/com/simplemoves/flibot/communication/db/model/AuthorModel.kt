package com.simplemoves.flibot.communication.db.model

import com.simplemoves.flibot.communication.db.table.AuthorNameTable
import com.simplemoves.flibot.utils.escapeMarkdown
import org.jetbrains.exposed.sql.ResultRow

data class AuthorModel (
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val nickName: String,
) {
    companion object {
        fun fromRow(resultRow: ResultRow) = AuthorModel(
            firstName = resultRow[AuthorNameTable.firstName].escapeMarkdown(),
            middleName = resultRow[AuthorNameTable.middleName].escapeMarkdown(),
            lastName = resultRow[AuthorNameTable.lastName].escapeMarkdown(),
            nickName = resultRow[AuthorNameTable.nickName].escapeMarkdown()
        )
    }

    fun toTelegramMessage(): String {
        val nickNameStr = if (nickName.isNotBlank()) " (_${nickName}_)" else ""
        return "\n$firstName $middleName $lastName$nickNameStr"
    }
}
