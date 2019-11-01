package br.com.jujuhealth.data.model

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable
import java.util.*

data class User(
    val name: String? = "",
    val uId: String? = "",
    val email: String? = "",
    val providerId: String? = "",
    val birthday: Timestamp? = Timestamp(Date())
): Serializable{

    companion object{
        fun buildUser(currentUser: FirebaseUser?): User {
            return User(
                name = "",
                uId = currentUser?.uid,
                email = currentUser?.email,
                providerId = currentUser?.providerId,
                birthday = Timestamp(Date())
            )
        }
    }

}