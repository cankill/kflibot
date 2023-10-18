val config_version: String by project
val telegram_bot_version: String by project
val consume_xml_version: String by project
val coroutines_version: String by project
val ktor_version: String by project
val exposedVersion: String by project
val mariaDb_version: String by project
val hikari_version: String by project
val kotlin_logging_version: String by project
val logback_version: String by project
val mockk_version: String by project
val atrium_version: String by project


plugins {
    kotlin("jvm") version "1.9.10"
    application
}

group = "com.simplemoves.flibot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Config
    implementation("com.typesafe:config:$config_version")
    // Telegram
    implementation("eu.vendeli:telegram-bot:$telegram_bot_version")
    // XML parsing
    implementation("com.gitlab.mvysny.konsume-xml:konsume-xml:$consume_xml_version")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$coroutines_version")
    // Ktor client
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-xml:$ktor_version")
    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
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