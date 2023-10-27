import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val config_version: String by project
val telegram_bot_version: String by project
val consume_xml_version: String by project
val coroutines_version: String by project
val ktor_version: String by project
val exposed_version: String by project
val mariaDb_version: String by project
val hikari_version: String by project
val kotlin_logging_version: String by project
val logback_version: String by project
val mockk_version: String by project
val atrium_version: String by project
val caffeine_version: String by project


plugins {
    kotlin("jvm") version "1.9.10"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.simplemoves.flibot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Config
    implementation("com.typesafe:config:$config_version")
    // Telegram
//    implementation("eu.vendeli:telegram-bot:$telegram_bot_version")
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:$telegram_bot_version") {
        exclude("com.squareup.okhttp3", "logging-interceptor")
    }

    // XML parsing
    implementation("com.gitlab.mvysny.konsume-xml:konsume-xml:$consume_xml_version")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$coroutines_version")
    // Retrofit
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    // Ktor client
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
//    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-xml:$ktor_version")
    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-json:$exposed_version")
    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine:$caffeine_version")
    // DB
    implementation("org.mariadb.jdbc:mariadb-java-client:$mariaDb_version")
    implementation("com.zaxxer:HikariCP:$hikari_version")
    //Logging
    implementation("io.github.oshai:kotlin-logging-jvm:$kotlin_logging_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Tests
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-client-mock:$ktor_version")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("ch.tutteli.atrium:atrium-fluent:$atrium_version")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.simplemoves.flibot.MainKt")
}

tasks.named("compileKotlin", org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask::class.java) {
    compilerOptions {
        freeCompilerArgs.add("-Xdebug")
    }
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set(project.name)
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.simplemoves.flibot.MainKt"))
        }
    }
}