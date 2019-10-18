package br.com.jujuhealth.data.model

import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

data class User(
    val name: String? = "",
    val uId: String? = "",
    val email: String? = "",
    val providerId: String? = "",
    val birthday: String? = ""
): Serializable{

    companion object{
        fun buildUser(currentUser: FirebaseUser?): User {
            return User(
                name = "",
                uId = currentUser?.uid,
                email = currentUser?.email,
                providerId = currentUser?.providerId,
                birthday = ""
            )
        }
    }

}