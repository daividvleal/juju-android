package br.com.jujuhealth.features.main

import androidx.lifecycle.ViewModel
import br.com.jujuhealth.extension.log
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.core.KoinComponent

class MainViewModel(private val firebaseAnalytics: FirebaseAnalytics): ViewModel(), KoinComponent{

    fun log(name: String){
        firebaseAnalytics.log(name)
    }

}