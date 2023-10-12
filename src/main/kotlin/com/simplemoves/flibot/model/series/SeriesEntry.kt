package com.simplemoves.flibot.model.series

import com.gitlab.mvysny.konsumexml.Konsumer
import com.gitlab.mvysny.konsumexml.Names
import com.gitlab.mvysny.konsumexml.Whitespace
import com.gitlab.mvysny.konsumexml.allChildrenAutoIgnore
import io.github.oshai.kotlinlogging.KotlinLogging

data class SeriesPath(val next: String): SeriesEntry
data class Series(val id: String, val description: String?): SeriesEntry

private val logger = KotlinLogging.logger {  }
sealed interface SeriesEntry {
    companion object {
        fun xml(k: Konsumer): SeriesEntry? {
            k.checkCurrent("entry")

            var title: String? = null
            var tag: String? = null
            var found: Boolean = false

            k.allChildrenAutoIgnore(Names.of("id", "title")) {
                when(localName) {
                    "id" -> {
                        val id = text(Whitespace.preserve).split(":")
                        found = id[1] == "sequence"
                        tag = id[2]
                    }
                    "title" -> title = text()
                }
            }

            return tag?.run { if (found) Series(this, title) else SeriesPath(this) }
        }
    }
}