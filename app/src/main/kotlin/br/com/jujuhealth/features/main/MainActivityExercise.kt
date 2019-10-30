package br.com.jujuhealth.features.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.jujuhealth.*
import br.com.jujuhealth.data.model.TrainingModel
import br.com.jujuhealth.extension.log
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main_exercise.*
import org.koin.android.ext.android.inject

class MainActivityExercise : AppCompatActivity(){

    private val firebaseAnalytics: FirebaseAnalytics by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_exercise)

        hard_exercise.setOnClickListener {
            firebaseAnalytics.log("btn_fast_train", "fast_train", "pressed_fast_train")
            startActivity(TrainingModel.Mode.FAST)
        }

        soft_exercise.setOnClickListener {
            firebaseAnalytics.log("btn_slow_train", "slow_train", "pressed_slow_train")
            startActivity(TrainingModel.Mode.SLOW)
        }
    }

    private fun startActivity(mode: TrainingModel.Mode){
        activeMode = when(mode) {
            TrainingModel.Mode.SLOW -> {
                slowEasy
            }
            TrainingModel.Mode.FAST -> {
                fastEasy
            }
        }
        startActivity(Intent(this, HostMainActivity::class.java))
        finish()
    }

}