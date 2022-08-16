package com.example

import com.example.routes.authRoutes
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import repository.DatabaseFactory
import routes.userRoutes

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    val databaseFactory = DatabaseFactory()

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        userRoutes(databaseFactory)
        authRoutes(databaseFactory)
    }
}