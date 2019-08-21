package br.com.jujuhealth.features.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.jujuhealth.R
import br.com.jujuhealth.features.HostSignActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this, HostSignActivity::class.java))
    }
}
