package com.simplemoves.flibot.model.series

import com.gitlab.mvysny.konsumexml.konsumeXml
import com.simplemoves.flibot.communication.KHttpClientProvider
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.net.URLEncoder
import java.nio.charset.Charset

private val logger = KotlinLogging.logger {  }
class SeriesRepo(private val clientProvider: KHttpClientProvider) {
    companion object {
        const val SEARCH_URL = "http://flibusta.is/opds/sequencesindex/"
        const val MAX_SEARCH_DEPTH = 10
    }

    suspend fun getSeries(searchText: String): List<SeriesEntry> {
        val seriesEntries = dig(listOf(SeriesPath(searchText)))
        return seriesEntries.filterIsInstance(Series::class.java)
    }

    private tailrec suspend fun dig(seriesPaths: List<SeriesPath>, result: List<Series> = listOf(), depth: Int = 0): List<SeriesEntry> {
        if (depth > MAX_SEARCH_DEPTH || seriesPaths.isEmpty()) return result

        val seriesFeed = seriesPaths.map { searchPath ->
            val url = SEARCH_URL + URLEncoder.encode(searchPath.next, Charset.defaultCharset())
            val response = clientProvider.client.get(url)
            if (response.status == HttpStatusCode.OK) {
                response.body<String>().konsumeXml().child("feed", SeriesFeed::xml).entries
            } else {
                listOf()
            }
        }.flatten()

        val series = seriesFeed.filterIsInstance<Series>()
        val nextSeriesPaths = seriesFeed.filterIsInstance<SeriesPath>()

        return dig(nextSeriesPaths, result + series, depth + 1)
    }
}