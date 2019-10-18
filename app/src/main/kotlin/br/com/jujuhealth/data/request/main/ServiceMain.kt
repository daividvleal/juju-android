package br.com.jujuhealth.data.request.main

import br.com.jujuhealth.data.model.User
import br.com.jujuhealth.data.request.COLLECTION_USER
import com.google.firebase.firestore.FirebaseFirestore

class ServiceMain(private val database: FirebaseFirestore) : ServiceMainContract {

    override fun getUser(uid: String, success: (User?) -> Unit, error: (Exception?) -> Unit){
        database.collection(COLLECTION_USER).document(uid).get().addOnSuccessListener {
            success(it.toObject(User::class.java))
        }.addOnFailureListener {
            error(it)
        }
    }

}