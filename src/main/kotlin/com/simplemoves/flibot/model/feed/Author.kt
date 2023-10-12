package com.simplemoves.flibot.model.feed

import com.gitlab.mvysny.konsumexml.Konsumer
import com.gitlab.mvysny.konsumexml.Names
import com.gitlab.mvysny.konsumexml.allChildrenAutoIgnore
import java.net.URI

data class Author(val name: String?, val url: URI?) {
    companion object {
        fun xml(k: Konsumer): Author {
            k.checkCurrent("author")
            var name: String? = null
            var url: URI? = null

            k.allChildrenAutoIgnore(Names.of("name", "uri")) {
                when (localName) {
                    "name" -> name = text()
                    "uri" -> url = URI.create("http://flibusta.is").resolve(text())
                }
            }

            return Author(name, url)
        }
    }
}