package br.com.jujuhealth.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.features.auth.HostSignActivity
import br.com.jujuhealth.features.main.MainActivityExercise
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SPLASH_TIME = 1500L

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setObservable()

        Handler().postDelayed({
            viewModel.isUserLogged()
        }, SPLASH_TIME)

    }

    private fun setObservable() {
        viewModel.getBaseModel()
            .observe(this, Observer {
                when (it.status) {
                    BaseModel.Status.SUCCESS -> {
                        startActivity(Intent(this, MainActivityExercise::class.java))
                        finish()
                    }
                    else -> {
                        startActivity(Intent(this, HostSignActivity::class.java))
                        finish()
                    }
                }
            })
    }

}
