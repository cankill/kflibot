package com.simplemoves.flibot.communication.db.table

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable

object SeriesNameTable: IntIdTable("libseqname", "SeqId"){
//    override val id         = uinteger("SeqId").entityId()
//    override val primaryKey = PrimaryKey(id)
    val seriesName          = varchar("SeqName", 254).default("").uniqueIndex("SeqName_2")
    val all = listOf(id, seriesName)
}