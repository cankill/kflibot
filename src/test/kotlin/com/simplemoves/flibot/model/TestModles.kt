//package com.simplemoves.flibot.model
//
//import com.gitlab.mvysny.konsumexml.konsumeXml
//import com.simplemoves.flibot.AbstractTest
//import com.simplemoves.flibot.model.feed.Feed
//import com.simplemoves.flibot.model.feed.Language
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import java.net.URI
//
//class TestModeles: AbstractTest() {
//    val xml = """<?xml version="1.0" encoding="utf-8"?>
//                    <feed xmlns="http://www.w3.org/2005/Atom" xmlns:dc="http://purl.org/dc/terms/"
//                      xmlns:os="http://a9.com/-/spec/opensearch/1.1/" xmlns:opds="http://opds-spec.org/2010/catalog">
//                      <id>tag:search:books:А. Смолин:</id>
//                      <title>Результат поиска</title>
//                      <updated>2023-09-28T19:09:01+02:00</updated>
//                      <icon>/favicon.ico</icon>
//                      <link href="/opds-opensearch.xml" rel="search" type="application/opensearchdescription+xml" />
//                      <link href="/opds/search?searchTerm={searchTerms}" rel="search" type="application/atom+xml" />
//                      <link href="/opds" rel="start" type="application/atom+xml;profile=opds-catalog" />
//                      <link href="/opds/search?searchTerm=%D0%90.%20%D0%A1%D0%BC%D0%BE%D0%BB%D0%B8%D0%BD" rel="up"
//                        type="application/atom+xml;profile=opds-catalog" />
//                      <entry>
//                        <updated>2023-09-28T19:09:01+02:00</updated>
//                        <title>А. Смолин, ведьмак [Компиляция, книги 1-5]</title>
//                        <author>
//                          <name>Васильев Андрей Александрович</name>
//                          <uri>/a/168147</uri>
//                        </author>
//                        <link href="/opds/author/168147" rel="related" type="application/atom+xml"
//                          title="Все книги автора Васильев Андрей Александрович" />
//                        <category term="Городское фэнтези" label="Городское фэнтези" />
//                        <dc:language>ru</dc:language>
//                        <dc:format>fb2+zip</dc:format>
//                        <dc:issued>2020</dc:issued>
//                        <link href="/opds/sequencebooks/53672" rel="related" type="application/atom+xml"
//                          title="Все книги серии &quot;А.Смолин, ведьмак&quot;" />
//                        <link href="/opds/sequencebooks/85234" rel="related" type="application/atom+xml"
//                          title="Все книги серии &quot;Вселенная мира Ночи&quot;" />
//                        <content type="text/html">
//                          &lt;p class=&quot;book&quot;&gt;…Что можно получить, совершив доброе дело? Например —
//                          благодарность. Или — похвалу. А может — просто хорошее настроение. Но это если все пойдет так,
//                          как у людей водится. Но если нет… Вот Александр Смолин, обычный московский парень, работающий
//                          среднестатистическим клерком в банке, помог вроде бы самому обычному старику, когда тому стало
//                          плохо на улице. Правда, помощь запоздала, старик умер. Казалось бы — штатная ситуация. Но
//                          старик тот возьми, да и окажись ведьмаком. А тем перед смертью непременно кому-то свою
//                          ведьмачью силу передать надо, вот Смолин и попал под ее раздачу. И тут такое
//                          началось…&lt;/p&gt;
//                          &lt;br&gt;
//                          &lt;p class=&quot;book&quot;&gt;Содержание сборника:&lt;/p&gt;
//                          &lt;p class=&quot;book&quot;&gt;1. Чужая сила.&lt;/p&gt;
//                          &lt;p class=&quot;book&quot;&gt;2. Знаки ночи.&lt;/p&gt;
//                          &lt;p class=&quot;book&quot;&gt;3. Тень света.&lt;/p&gt;
//                          &lt;p class=&quot;book&quot;&gt;4. Час полнолуния.&lt;/p&gt;
//                          &lt;p class=&quot;book&quot;&gt;5. Темное время.&lt;/p&gt;
//                          &lt;br/&gt;Год издания: 2020&lt;br/&gt;Формат: fb2&lt;br/&gt;Язык: ru&lt;br/&gt;Размер: 5973
//                          Kb&lt;br/&gt;Скачиваний: 24927&lt;br/&gt;Серия: А.Смолин, ведьмак&lt;br/&gt;Серия: Вселенная
//                          мира Ночи #1&lt;br/&gt;</content>
//                        <link href="/i/9/592309/cover.jpg" rel="http://opds-spec.org/image" type="image/jpeg" />
//                        <link href="/i/9/592309/cover.jpg" rel="x-stanza-cover-image" type="image/jpeg" />
//                        <link href="/i/9/592309/cover.jpg" rel="http://opds-spec.org/thumbnail" type="image/jpeg" />
//                        <link href="/i/9/592309/cover.jpg" rel="x-stanza-cover-image-thumbnail" type="image/jpeg" />
//                        <link href="/b/592309/fb2" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/fb2+zip" />
//                        <link href="/b/592309/html" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/html+zip" />
//                        <link href="/b/592309/txt" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/txt+zip" />
//                        <link href="/b/592309/rtf" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/rtf+zip" />
//                        <link href="/b/592309/epub" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/epub+zip" />
//                        <link href="/b/592309/mobi" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/x-mobipocket-ebook" />
//                        <link href="/b/592309" rel="alternate" type="text/html" title="Книга на сайте" />
//                        <id>tag:book:658d2bdd31162cf5caaa318fcb70a6b8</id>
//                      </entry>
//                      <entry>
//                        <updated>2023-09-28T19:09:01+02:00</updated>
//                        <title>Антология Сатиры и Юмора России XX века. Том 31. Ефим Смолин</title>
//                        <author>
//                          <name>Смолин Ефим Маркович</name>
//                          <uri>/a/75052</uri>
//                        </author>
//                        <link href="/opds/author/75052" rel="related" type="application/atom+xml"
//                          title="Все книги автора Смолин Ефим Маркович" />
//                        <category term="Юмористическая проза" label="Юмористическая проза" />
//                        <dc:language>ru</dc:language>
//                        <dc:format>fb2+zip</dc:format>
//                        <dc:issued>2004</dc:issued>
//                        <content type="text/html">Год издания: 2004&lt;br/&gt;Формат: fb2&lt;br/&gt;Язык:
//                          ru&lt;br/&gt;Размер: 1981 Kb&lt;br/&gt;Скачиваний: 586&lt;br/&gt;</content>
//                        <link href="/i/98/735498/cover.jpg" rel="http://opds-spec.org/image" type="image/jpeg" />
//                        <link href="/i/98/735498/cover.jpg" rel="x-stanza-cover-image" type="image/jpeg" />
//                        <link href="/i/98/735498/cover.jpg" rel="http://opds-spec.org/thumbnail" type="image/jpeg" />
//                        <link href="/i/98/735498/cover.jpg" rel="x-stanza-cover-image-thumbnail" type="image/jpeg" />
//                        <link href="/b/735498/fb2" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/fb2+zip" />
//                        <link href="/b/735498/html" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/html+zip" />
//                        <link href="/b/735498/txt" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/txt+zip" />
//                        <link href="/b/735498/rtf" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/rtf+zip" />
//                        <link href="/b/735498/epub" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/epub+zip" />
//                        <link href="/b/735498/mobi" rel="http://opds-spec.org/acquisition/open-access"
//                          type="application/x-mobipocket-ebook" />
//                        <link href="/b/735498" rel="alternate" type="text/html" title="Книга на сайте" />
//                        <id>tag:book:deb39336f9025b62888f19c85c659b10</id>
//                      </entry>
//                    </feed>"""
//
//    @Test
//    fun testFeed() {
//        val feed = xml.konsumeXml().child("feed", Feed::xml)
//
//
//        assertThat(feed.entries).hasSize(2)
//        with(feed.entries[0]) {
//            assertThat(language).isEqualTo(Language("ru"))
//            assertThat(title).isEqualTo("А. Смолин, ведьмак [Компиляция, книги 1-5]")
//            assertThat(category).isNotNull
//            category?.run {
//                assertThat(term).isEqualTo("Городское фэнтези")
//                assertThat(label).isEqualTo("Городское фэнтези")
//            }
//            assertThat(author).isNotNull
//            author?.run {
//                assertThat(name).isEqualTo("Васильев Андрей Александрович")
//                assertThat(url).isEqualTo(URI("http://flibusta.is/a/168147"))
//            }
//        }
//    }
//}