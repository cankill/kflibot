package com.simplemoves.flibot

import com.simplemoves.flibot.communication.KHttpClientProvider
import com.simplemoves.flibot.model.series.SeriesRepo
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}
suspend fun main(args: Array<String>) {
    val clientProvider = KHttpClientProvider()
    val seriesRepo = SeriesRepo(clientProvider)

    val result = seriesRepo.getSeries("Блюстител")

    logger.debug { result }
}

