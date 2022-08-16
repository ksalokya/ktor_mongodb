package com.example.routes

import data.User
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.mindrot.jbcrypt.BCrypt
import repository.DatabaseFactory

fun Route.authRoutes(
    db: DatabaseFactory
) {
    route("/signin") {
        post {
            // extracting json data from URL payload
            val requestBody = call.receive<User>()
            val hashed = db.getPasswordByEmail(requestBody.email!!)
            // Check that an unencrypted password matches one that has
            // previously been hashed
            if (BCrypt.checkpw(requestBody.password, hashed[0].password))
                call.respondText { "Password matches..." }
            else
                call.respondText { "Password doesn't matches..." }
        }
    }

    route("/signup") {
        post{
            // extracting json data from URL payload
            val requestBody = call.receive<User>()
            // hashing password
            val hashed = BCrypt.hashpw(requestBody.password, BCrypt.gensalt())
            // adding user to DB with hashed password
            val newUser = User(requestBody.username, requestBody.email, hashed)
            val user = db.addUser(newUser)
            call.respond(user)
        }
    }
}