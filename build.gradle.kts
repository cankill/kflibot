val ktor_version: String by project

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
    implementation("com.typesafe:config:1.4.2")

    implementation("eu.vendeli:telegram-bot:3.1.0")

    implementation("com.gitlab.mvysny.konsume-xml:konsume-xml:1.0")


//    implementation("io.arrow-kt:arrow-core:1.2.1")
//    implementation("io.arrow-kt:arrow-fx-coroutines:1.2.1")
//    implementation("io.arrow-kt:arrow-fx-resilience:1.1.6-alpha.81")
//    implementation("io.arrow-kt:arrow-core-extensions:0.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk9:1.7.3")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("ch.qos.logback:logback-classic:1.4.11")

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
//    implementation("io.ktor:ktor-client-jetty:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-xml:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.4")

    testImplementation("io.ktor:ktor-client-mock:$ktor_version")
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.7")
    testImplementation("org.assertj:assertj-core:3.24.2")
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