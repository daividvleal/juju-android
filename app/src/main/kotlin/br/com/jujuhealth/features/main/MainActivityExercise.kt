package br.com.jujuhealth.features.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.jujuhealth.R
import br.com.jujuhealth.activeMode
import br.com.jujuhealth.data.model.TrainingModel
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_FAST_TRAIN
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_SLOW_TRAIN
import br.com.jujuhealth.fastEasy
import br.com.jujuhealth.slowEasy
import kotlinx.android.synthetic.main.activity_main_exercise.*
import org.koin.android.ext.android.inject

class MainActivityExercise : AppCompatActivity(){

    private val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_exercise)

        hard_exercise.setOnClickListener {
            mainViewModel.log(FIREBASE_EVENT_PRESSED_FAST_TRAIN)
            startActivity(TrainingModel.Mode.FAST)
        }

        soft_exercise.setOnClickListener {
            mainViewModel.log(FIREBASE_EVENT_PRESSED_SLOW_TRAIN)
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