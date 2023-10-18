package com.simplemoves.flibot.communication.db.connection

import com.simplemoves.flibot.config.DbConfiguration
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

class MariaDbConnection(config: DbConfiguration) {
    private val hikariConfig = HikariConfig().apply {
        jdbcUrl = config.jdbcUrl
        username = config.user
        password = config.password
        maximumPoolSize = config.poolSize
    }

    private val dataSource =HikariDataSource(hikariConfig)

    init {
        Database.connect(dataSource)
    }
}