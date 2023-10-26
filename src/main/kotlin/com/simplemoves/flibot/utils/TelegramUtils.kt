package com.simplemoves.flibot.utils

val TELEGRAM_SPECIAL_CHARS = listOf("\\", "_", "*", "[", "]", "(", ")", "~", "`", ">", "<", "&", "#", "+", "-", "=", "|", "{", "}", ".", "!" )
//val TELEGRAM_SPECIAL_CHARS = listOf("\\", "_", "*", "[", "]", "(", ")", "~", "`", ">", "<", "&", "#", "+", "-", "=", "|", "{", "}", ".", "!" )

fun String.escapeMarkdown(): String =
    TELEGRAM_SPECIAL_CHARS.fold(this) { acc, char -> acc.replace(char, "\\$char") }