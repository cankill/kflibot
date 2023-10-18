package com.simplemoves.flibot.communication.db.table

import org.jetbrains.exposed.sql.Table

object AuthorTable: Table("libavtor"){
    val bookId   = reference("BookId", BookTable.id)
    val authorId = reference("AvtorId", AuthorNameTable.id).index("iav")
    override val primaryKey = PrimaryKey(bookId, authorId)
}