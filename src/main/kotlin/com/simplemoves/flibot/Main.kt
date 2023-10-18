package com.simplemoves.flibot

import com.simplemoves.flibot.communication.db.connection.MariaDbConnection
import com.simplemoves.flibot.communication.db.repo.AuthorRepo
import com.simplemoves.flibot.communication.db.repo.BookRepo
import com.simplemoves.flibot.communication.db.repo.SeriesRepo
import com.simplemoves.flibot.config.Configuration
import com.typesafe.config.ConfigFactory
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}
suspend fun main(args: Array<String>) {
    val configFactory = ConfigFactory.load()
    val config = configFactory.getConfig("flibot").withFallback(configFactory)
    val configuration = Configuration(config)

    MariaDbConnection(configuration.db)

//    val clientProvider = KHttpClientProvider()
    val seriesRepo = SeriesRepo()
    val authorRepo = AuthorRepo()
    val bookRepo = BookRepo()

    val result = seriesRepo.findSeries("Столица мятежной окраины")
    val result2 = authorRepo.findAuthor("Столица мятежной окраины")
    val result3 = bookRepo.findBooks("Столица мятежной окраины")

    logger.debug { result3 }
}

