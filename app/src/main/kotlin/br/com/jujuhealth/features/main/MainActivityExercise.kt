package br.com.jujuhealth.features.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.jujuhealth.*
import br.com.jujuhealth.data.model.TrainingModel
import kotlinx.android.synthetic.main.activity_main_exercise.*

class MainActivityExercise : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_exercise)

        hard_exercise.setOnClickListener {
            startActivity(TrainingModel.Mode.FAST)
        }

        soft_exercise.setOnClickListener {
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