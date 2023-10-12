package com.simplemoves.flibot.model.feed

import com.gitlab.mvysny.konsumexml.Konsumer

@JvmInline
value class Language(val code: String) {
    companion object {
        fun xml(k: Konsumer): Language {
            k.checkCurrent("language")
            return Language(k.text())
        }
    }
}
