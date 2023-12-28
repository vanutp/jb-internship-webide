package dev.vanutp.jb_internship_webide.plugins

import dev.vanutp.jb_internship_webide.dataDir
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.nio.file.Path
import kotlin.io.path.*

fun getFilePath(): Path {
    return dataDir.resolve("source.ts")
}

fun Application.configureRouting() {
    routing {
        get("/file.ts") {
            val path = getFilePath()
            if (path.notExists()) {
                path.createFile()
            }
            call.respondText(path.readText(), ContentType.Text.Plain)
        }
        put("/file.ts") {
            getFilePath().writeText(call.receiveText())
            call.respond(204)
        }
    }
}
