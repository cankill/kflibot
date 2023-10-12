package com.simplemoves.flibot.model.series

import com.gitlab.mvysny.konsumexml.Konsumer
import com.gitlab.mvysny.konsumexml.allChildrenAutoIgnore

class SeriesFeed(val entries: List<SeriesEntry>) {
    companion object {
        fun xml(k: Konsumer): SeriesFeed {
            k.checkCurrent("feed")
            val entries = mutableListOf<SeriesEntry>()

            k.allChildrenAutoIgnore("entry") { SeriesEntry.xml(this)?.run(entries::add) }
            return SeriesFeed(entries)
        }
    }
}