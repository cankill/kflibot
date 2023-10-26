package com.simplemoves.flibot.communication.http

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.xml.*

class KHttpClientProvider {
    val client = HttpClient(OkHttp) {
        followRedirects = true

        engine {
//            sslContextFactory = SslContextFactory.Client()
//            clientCacheSize = 12
            proxy = ProxyBuilder.socks("0.0.0.0", 9050)
        }

        install(HttpTimeout) {
            socketTimeoutMillis = 10_000
            connectTimeoutMillis = 10_000
            requestTimeoutMillis = 10_000
        }

        install(ContentNegotiation) {
            xml()
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }
}