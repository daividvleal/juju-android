package br.com.jujuhealth.data.request.main

import br.com.jujuhealth.data.model.User
import br.com.jujuhealth.data.request.COLLECTION_USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ServiceMain(private val database: FirebaseFirestore, private val auth: FirebaseAuth) : ServiceMainContract {

    override fun getUser(success: (User?) -> Unit, error: (Exception?) -> Unit){
        auth.currentUser?.uid?.let { uid ->
            database.collection(COLLECTION_USER).document(uid).get().addOnSuccessListener {
                success(it.toObject(User::class.java))
            }.addOnFailureListener {
                error(it)
            }
        } ?: run {
            error(java.lang.Exception())
        }
    }

}