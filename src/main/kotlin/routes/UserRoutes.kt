package routes

import data.User
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import repository.DatabaseFactory

fun Route.userRoutes(
    db: DatabaseFactory
) {
    route("/user") {

        // get all user
        get {
            // getting all users from database
            val users = db.getAllUser()
            call.respond(users)
        }

        // get user by ID
        get("/{id}") {
            // parsing id parameter from URL
            val id = call.parameters["id"]
            // getting user by ID
            val user = db.getUserById(id!!) // null safety
            call.respond(user)
        }

        // create user
        post {
            // extracting json data from URL payload
            val requestBody = call.receive<User>()
            val user = db.addUser(requestBody)
            call.respond(user)
        }

        // update user
        patch {
            // extracting json data from URL payload
            val requestBody = call.receive<User>()
            // updating user by ID
            val isUpdated = db.updateUserById(requestBody)

            if (isUpdated)
                call.respondText("User updated successfully!")
            else
                call.respondText("ID not found!")
        }

        // delete user
        delete("/{id}") {
            // parsing id parameter from URL
            val id = call.parameters["id"]
            // deleting user by ID
            val isDeleted = db.deleteUserById(id!!)
            if (isDeleted)
                call.respondText("User deleted successfully!")
            else
                call.respondText("ID not found!")
        }
    }
}
