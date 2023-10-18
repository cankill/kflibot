package com.simplemoves.flibot.communication.db.table

import org.jetbrains.exposed.dao.id.IdTable

object AuthorNameTable: IdTable<UInt>("libavtorname"){
    override val id         = uinteger("AvtorId").entityId()
    override val primaryKey = PrimaryKey(id)
    val firstName           = varchar("FirstName", 99).default("").index("FirstName")
    val middleName          = varchar("MiddleName", 99).default("")
    val lastName            = varchar("LastName", 99).default("").index("LastName")
    val nickName            = varchar("NickName", 33).default("")
}