package com.simplemoves.flibot.config

import com.typesafe.config.Config

class Configuration(config: Config) {
    val db = DbConfiguration(config.getConfig("db"))
    val proxy = ProxyConfiguration(config.getConfig("proxy"))
    val telegramBot = TelegramBotConfiguration(config.getConfig("telegram-bot"))

    val tmpDir = kotlin.io.path.createTempDirectory("kflibot")
    init {
        tmpDir.toFile().deleteOnExit()
    }
}

class DbConfiguration(config: Config) {
    val host = config.getString("host")
    val port = config.getInt("port")
    val name = config.getString("name")
    val user = config.getString("user")
    val password = config.getString("password")
    val dirver = config.getString("driver")
    val poolSize = config.getInt("pool-size")

    val jdbcUrl = "jdbc:mariadb://$host:$port/$name"
}

class ProxyConfiguration(config: Config) {
    val host = config.getString("host")
    val port = config.getInt("port")
}

class TelegramBotConfiguration(config: Config) {
    val authToken = config.getString("auth-token")
    val timeout = config.getInt("timeout")
}