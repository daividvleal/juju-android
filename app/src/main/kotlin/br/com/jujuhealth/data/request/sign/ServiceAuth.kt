package br.com.jujuhealth.data.request.sign

import android.util.Log
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.data.model.User
import br.com.jujuhealth.data.request.COLLECTION_DIARY
import br.com.jujuhealth.data.request.COLLECTION_TRAINING_DIARY
import br.com.jujuhealth.data.request.COLLECTION_USER
import br.com.jujuhealth.data.request.DATE_FORMAT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class ServiceAuth(private val auth: FirebaseAuth, private val database: FirebaseFirestore) :
    ServiceAuthContract {

    override fun signUp(
        name: String,
        birthday: String,
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Exception?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d("FIREBASE:", "createUserWithEmail:success")
                    val id = auth.currentUser?.uid?.let {
                        it
                    } ?: run { email }

                    database.collection(COLLECTION_USER).document(id).set(
                        User(
                            name = name,
                            birthday = birthday,
                            email = email,
                            providerId = auth.currentUser?.providerId,
                            uId = auth.currentUser?.uid
                        )
                    )

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FIREBASE:", "createUserWithEmail:failure", task.exception)
                    error(task.exception)
                }
            }
    }

    override fun signIn(
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Exception?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d("FIREBASE:", "signInWithEmail:success")
                    success(auth.currentUser)
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.d("FIREBASE:", "signInWithEmail:failure", task.exception)
                    error(task.exception)
                }
            }
    }

    override fun checkUserLogged(
        success: (FirebaseUser?) -> Unit,
        error: () -> Unit
    ) {
        auth.currentUser?.let {
            success(it)
        } ?: run {
            error()
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun updatePassword(pwdActual: String, pwd: String, success: () -> Unit, error: (Exception?) -> Unit) {
        auth.signInWithEmailAndPassword(auth.currentUser?.email!!, pwdActual)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d("FIREBASE:", "signInWithEmail:success")
                    auth.currentUser?.updatePassword(pwd)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d("FIREBASE:", "signInWithEmail:success")
                                success()
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.d("FIREBASE:", "signInWithEmail:failure", task.exception)
                                error(task.exception)
                            }
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.d("FIREBASE:", "signInWithEmail:failure", task.exception)
                    error(task.exception)
                }
            }
    }

}
