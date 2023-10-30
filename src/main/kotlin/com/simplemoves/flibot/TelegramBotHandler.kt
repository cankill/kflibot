package com.simplemoves.flibot

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.*
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.extensions.filters.Filter
import com.github.kotlintelegrambot.logging.LogLevel
import com.simplemoves.flibot.communication.db.model.BookModel
import com.simplemoves.flibot.communication.db.repo.ReposHolder
import com.simplemoves.flibot.communication.http.KHttpClientProvider
import com.simplemoves.flibot.config.Configuration
import com.simplemoves.flibot.representation.BooksPageRepresentation
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

class TelegramBotHandler(configuration: Configuration, private val repos: ReposHolder, clientProvider: KHttpClientProvider, botLogLevel: LogLevel = LogLevel.All()) {
    val booksPageRepresentation = BooksPageRepresentation(5)
    val queryCache: LoadingCache<String, List<BookModel>> = Caffeine.newBuilder()
        .maximumSize(1_000)
        .expireAfterWrite(30.minutes.toJavaDuration())
        .build { query -> repos.search(query)}

    val queryKeysCache: Cache<String, String> = Caffeine.newBuilder()
        .maximumSize(1_000)
        .expireAfterWrite(30.minutes.toJavaDuration())
        .build()

    val BARREL_DOWNLOAD_REGEXP = """/barrel(?<cacheId>\d+)of(?<index>\d+)""".toRegex()
    val BARREL_LINK_REGEXP = """/b/(?<bookId>\d+)/(?<format>(fb2|epub|mobi))""".toRegex()
    val PAGE_COMMAND_REGEXP = """/query/(?<cacheId>\d+)/page/(?<page>\d+)""".toRegex()

    val bot = bot {
        token = configuration.telegramBot.authToken
        timeout = configuration.telegramBot.timeout
        logLevel = botLogLevel

        dispatch {
            callbackQuery {
                callbackQuery.data.let { data ->
                    PAGE_COMMAND_REGEXP.matchEntire(data)?.groups?.let { groups ->
                        groups["cacheId"]?.value?.let { cacheId ->
                            queryKeysCache.getIfPresent(cacheId)?.let { cacheKey ->
                                groups["page"]?.value?.run { toIntOrNull() }?.let { currentPageNumber ->
                                    queryCache.get(cacheKey)?.let { books ->
                                        val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                                        val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

                                        val (page, inlineKeyboardMarkup) = booksPageRepresentation.getPage(currentPageNumber, cacheId, books)

                                        bot.editMessageText(
                                            ChatId.fromId(chatId),
                                            messageId,
                                            text = page,
                                            replyMarkup = inlineKeyboardMarkup,
                                            parseMode = ParseMode.MARKDOWN_V2)
                                    }
                                }
                            }
                        }
                    }
                    BARREL_LINK_REGEXP.matchEntire(data)?.groups?.let { groups ->
                        val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                        groups["bookId"]?.value?.run { toIntOrNull() }?.let { bookId ->
                            groups["format"]?.value?.let { format ->
                                val outputFile = configuration.tmpDir.resolve("$bookId.zip").toFile()
                                val file = clientProvider.client.get("http://flibusta.site/b/$bookId/fb2").bodyAsChannel().copyAndClose(outputFile.writeChannel())
                                bot.sendDocument(ChatId.fromId(chatId), TelegramFile.ByFile(outputFile))
                            }
                        }
                    }
                }
            }
            message(Filter.Command) {
                message.text?.let { text ->
                    BARREL_DOWNLOAD_REGEXP.matchEntire(text)?.groups?.let { groups ->
                        groups["cacheId"]?.value?.let { cacheId ->
                            queryKeysCache.getIfPresent(cacheId)?.let { cacheKey ->
                                groups["index"]?.value?.run { toIntOrNull() }?.let { index ->
                                    queryCache.get(cacheKey)?.get(index)?.let { book ->
                                        val bookUrl = "/b/${book.id}"
                                        val inlineKeyboardMarkup = InlineKeyboardMarkup.createSingleRowKeyboard(
                                            InlineKeyboardButton.CallbackData(text = "fb2", callbackData = "$bookUrl/fb2"),
                                            InlineKeyboardButton.CallbackData(text = "epub", callbackData = "$bookUrl/epub"),
                                            InlineKeyboardButton.CallbackData(text = "mobi", callbackData = "$bookUrl/mobi"))

                                        bot.sendMessage(
                                            ChatId.fromId(message.chat.id),
                                            text = book.toTelegramExtendedMessage(),
                                            replyMarkup = inlineKeyboardMarkup,
                                            parseMode = ParseMode.MARKDOWN_V2)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            message(Filter.Text) {
                message.text?.let { query ->
                    val books = queryCache.get(query)
                    val queryKey = query.hashCode().toUInt().toString()
                    queryKeysCache.put(queryKey, query)
                    val (page, inlineKeyboardMarkup) = booksPageRepresentation.getPage(0, queryKey, books)

                    bot.sendMessage(
                        ChatId.fromId(message.chat.id),
                        text = page,
                        replyMarkup = inlineKeyboardMarkup,
                        parseMode = ParseMode.MARKDOWN_V2)
                }
            }
            telegramError {
                println(error.getErrorMessage())
            }
        }
    }
}