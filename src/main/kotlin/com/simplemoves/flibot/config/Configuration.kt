package com.simplemoves.flibot.config

import com.typesafe.config.Config

class Configuration(config: Config) {
    val db = DbConfiguration(config.getConfig("db"))
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