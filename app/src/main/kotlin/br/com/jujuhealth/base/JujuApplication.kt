package br.com.jujuhealth.base

import android.app.Application
import br.com.jujuhealth.*
import br.com.jujuhealth.di.PROPERTY_BASE_URL
import br.com.jujuhealth.di.networkModule
import br.com.jujuhealth.di.repositoryModule
import br.com.jujuhealth.di.viewModelModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JujuApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@JujuApplication)
            modules(listOf(
                networkModule,
                repositoryModule,
                viewModelModule
            ))
            properties(mapOf(PROPERTY_BASE_URL to BuildConfig.BASE_URL))
        }
    }

}