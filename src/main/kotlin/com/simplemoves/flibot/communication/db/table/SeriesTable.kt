package com.simplemoves.flibot.communication.db.table

import org.jetbrains.exposed.sql.Table

object SeriesTable: Table("libseq"){
    val bookId   = reference("BookId", BookTable.id)
    val seriesId    = reference("SeqId", SeriesNameTable.id).index("SeqId")
    override val primaryKey = PrimaryKey(bookId, seriesId)
    val seriesNumber  = uinteger("SeqNumb")
    val all = listOf(bookId, seriesId, seriesNumber)
}