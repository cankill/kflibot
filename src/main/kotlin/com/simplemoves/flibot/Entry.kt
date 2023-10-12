package com.simplemoves.flibot

import java.time.Instant

data class Entry(val id: String, val author: String, val title: String, val links: List<Link> = listOf(), val updated: Instant = Instant.now())