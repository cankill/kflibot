package com.simplemoves.flibot.communication.db.table

import org.jetbrains.exposed.dao.id.IntIdTable

object BookTable: IntIdTable("libbook", "BookId"){
//    override val id         = uinteger("BookId").entityId().autoIncrement()
//    override val primaryKey = PrimaryKey(id)
    val title               = varchar("Title", 254).default("").index("Title")
    val language            = varchar("Lang", 3).default("ru").index("Lang")
    val fileType            = varchar("FileType", 4).index("FileType")
    val deleted             = char("Deleted").default('0').index("Deleted")
    val fileSize            = uinteger("FileSize").default(0u).index("FileSize")

    init {
        uniqueIndex("BookDel", deleted, id)
    }
}