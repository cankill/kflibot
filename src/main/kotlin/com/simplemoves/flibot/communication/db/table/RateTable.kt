package com.simplemoves.flibot.communication.db.table

import org.jetbrains.exposed.dao.id.IdTable

object RateTable: IdTable<Int>("librate"){
    override val id         = integer("ID").entityId()
    override val primaryKey = PrimaryKey(id)
    val bookId              = reference("BookId", BookTable.id).index("BookId1")
    val rate                = short("Rate")
}