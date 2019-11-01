package br.com.jujuhealth.data.request.sign

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser


interface ServiceAuthContract {

    fun signIn(
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Exception?) -> Unit
    )

    fun signUp(
        name: String,
        birthday: Timestamp,
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Exception?) -> Unit
    )

    fun signOut()

    fun checkUserLogged(
        success: (FirebaseUser?) -> Unit,
        error: () -> Unit
    )

    fun updatePassword(
        pwd_actual: String,
        pwd: String,
        success: () -> Unit,
        error: (Exception?) -> Unit
    )

}