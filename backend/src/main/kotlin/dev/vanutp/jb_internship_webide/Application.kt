package dev.vanutp.jb_internship_webide

import dev.vanutp.jb_internship_webide.plugins.configureHTTP
import dev.vanutp.jb_internship_webide.plugins.configureMonitoring
import dev.vanutp.jb_internship_webide.plugins.configureRouting
import dev.vanutp.jb_internship_webide.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createDirectory
import kotlin.io.path.notExists

val dataDir = Path(System.getenv().getOrDefault("DATA_DIR", "data"))

fun main() {
    val env = System.getenv()
    if (dataDir.notExists()) {
        dataDir.createDirectories()
    }
    embeddedServer(
        Netty,
        host = env.getOrDefault("LISTEN_HOST", "127.0.0.1"),
        port = env.getOrDefault("LISTEN_PORT", "8000").toInt(),
        module = Application::module,
    )
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
