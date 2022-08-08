package data

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

class User {
    // as userID is unique
    @BsonId
    val userId: String? = ObjectId().toString()
    val username: String? = null
    val email: String? = null
    val password: String? = null
}