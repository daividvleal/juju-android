package br.com.jujuhealth.base

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.multidex.MultiDexApplication
import br.com.jujuhealth.BuildConfig
import br.com.jujuhealth.R
import br.com.jujuhealth.di.PROPERTY_BASE_URL
import br.com.jujuhealth.di.networkModule
import br.com.jujuhealth.di.repositoryModule
import br.com.jujuhealth.di.viewModelModule
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JujuApplication : MultiDexApplication(){

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
        getDeviceToken()
    }

    private fun getDeviceToken(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_DEFAULT)
            )
        }

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
            })

        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.default_notification_channel_name))
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
            })
    }

}