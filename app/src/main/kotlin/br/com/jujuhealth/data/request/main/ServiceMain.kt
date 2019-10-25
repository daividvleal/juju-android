package br.com.jujuhealth.data.request.main

import br.com.jujuhealth.data.model.User
import br.com.jujuhealth.data.request.COLLECTION_USER
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class ServiceMain(private val database: FirebaseFirestore, private val user: FirebaseUser) : ServiceMainContract {

    override fun getUser(success: (User?) -> Unit, error: (Exception?) -> Unit){
        database.collection(COLLECTION_USER).document(user.uid).get().addOnSuccessListener {
            success(it.toObject(User::class.java))
        }.addOnFailureListener {
            error(it)
        }
    }

}