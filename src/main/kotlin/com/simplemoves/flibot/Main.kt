package com.simplemoves.flibot

import com.simplemoves.flibot.communication.http.KHttpClientProvider
import com.simplemoves.flibot.communication.db.connection.MariaDbConnection
import com.simplemoves.flibot.communication.db.repo.ReposHolder
import com.simplemoves.flibot.config.Configuration
import com.typesafe.config.ConfigFactory
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}
suspend fun main(args: Array<String>) {
    val configFactory = ConfigFactory.load()
    val config = configFactory.getConfig("flibot").withFallback(configFactory)
    val configuration = Configuration(config)

    MariaDbConnection(configuration.db)
    val clientProvider = KHttpClientProvider(configuration)
    val repos = ReposHolder()

    val telegramBot = TelegramBotHandler(configuration, repos, clientProvider)
    telegramBot.bot.startPolling()
}

