package data

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

class User {
    // as userID is unique
    @BsonId
    val userId: String? = ObjectId().toString()
    var username: String? = null
    var email: String? = null
    var password: String? = null

    constructor(name : String?, mail : String?, pwd : String?){
        this.username = name
        this.email = mail
        this.password = pwd
    }
}