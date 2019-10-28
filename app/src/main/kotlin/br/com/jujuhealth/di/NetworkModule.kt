package br.com.jujuhealth.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

const val PROPERTY_BASE_URL = "PROPERTY_BASE_URL"

val networkModule = module {
    single{
        FirebaseAuth.getInstance()
    }

    single {
        FirebaseFirestore.getInstance()
    }

}
