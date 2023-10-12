package com.simplemoves.flibot.model.feed

import com.gitlab.mvysny.konsumexml.Konsumer
import com.gitlab.mvysny.konsumexml.allChildrenAutoIgnore

data class Feed(val entries: List<Entry>) {
    companion object {
        fun xml(k: Konsumer): Feed {
            k.checkCurrent("feed")

            val entries = mutableListOf<Entry>()

            k.allChildrenAutoIgnore("entry") { entries.add(Entry.xml(this)) }

            k.skipContents()
            return Feed(entries)
        }
    }
}