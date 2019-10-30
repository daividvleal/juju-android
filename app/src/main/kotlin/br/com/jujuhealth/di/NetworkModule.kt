package br.com.jujuhealth.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.google.firebase.firestore.FirebaseFirestoreSettings



const val PROPERTY_BASE_URL = "PROPERTY_BASE_URL"

val networkModule = module {
    single{
        FirebaseAuth.getInstance()
    }

    single {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        fireStore
    }

    single{
       FirebaseAnalytics.getInstance(androidContext())
    }

}
