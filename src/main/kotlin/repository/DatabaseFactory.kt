package repository

import com.mongodb.client.result.UpdateResult
import data.User
import io.ktor.http.*
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class DatabaseFactory {
    // creating connection between client and MongoDB database
    private val client = KMongo.createClient().coroutine

    // fetching "admin" db
    private val database = client.getDatabase("admin")

    // creating/getting collection of type User
    val userCollection: CoroutineCollection<User> = database.getCollection()

    // create user
    suspend fun addUser(user: User): User {
        userCollection.insertOne(user)
        return user
    }

    // get all users
    suspend fun getAllUser(): List<User> = userCollection.find().toList()

    // get user by ID
    suspend fun getUserById(userId: String): List<User> {
        return userCollection.find(User::userId eq userId).toList()
    }

    // update user -> optimize it**
    suspend fun updateUserById(user: User): Boolean {
        val userExists = userCollection.findOneById(user.userId!!)
        if (userExists != null) {
            return userCollection.updateOneById(user.userId, user).wasAcknowledged()
        }
        return false
    }

    // delete user
    suspend fun deleteUserById(userId: String): Boolean {
        return userCollection.deleteOne(User::userId eq userId).wasAcknowledged()
    }

}