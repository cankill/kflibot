package com.simplemoves.flibot.model.feed

import com.gitlab.mvysny.konsumexml.Konsumer

data class Category(val term: String?, val label: String?) {
    companion object {
        fun xml(k: Konsumer): Category {
            k.checkCurrent("category")
            // k.attributes.getValueInt("index"), k.childText("text")
            val term: String? = k.attributes.getValueOrNull("term")
            val label: String? = k.attributes.getValueOrNull("label")

            return Category(term, label)
        }
    }
}
