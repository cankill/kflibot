package com.simplemoves.flibot.model.feed

import com.gitlab.mvysny.konsumexml.Konsumer
import com.gitlab.mvysny.konsumexml.Names
import com.gitlab.mvysny.konsumexml.allChildrenAutoIgnore

data class Entry(val title: String?, val language: Language?, val category: Category?, val author: Author?) {
    companion object {
        fun xml(k: Konsumer): Entry {
            k.checkCurrent("entry")
            var title: String? = null
            var category: Category? = null
            var author: Author? = null
            var language: Language? = null

            k.allChildrenAutoIgnore(Names.of("title", "category", "author", "language")) {
                when (localName) {
                    "title" -> title = text()
                    "category" -> category = Category.xml(this)
                    "author" -> author = Author.xml(this)
                    "language" -> language = Language.xml(this)
                }
            }

            return Entry(title, language, category, author)
        }
    }
}