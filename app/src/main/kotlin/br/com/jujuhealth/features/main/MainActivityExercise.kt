package br.com.jujuhealth.features.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.jujuhealth.*
import br.com.jujuhealth.data.model.TrainingModel
import br.com.jujuhealth.data.model.User
import kotlinx.android.synthetic.main.activity_main_exercise.*

class MainActivityExercise : AppCompatActivity(){

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_exercise)

        user = intent.getSerializableExtra(FIREBASE_USER) as User?
        hard_exercise.setOnClickListener {
            startActivity(TrainingModel.Mode.FAST, user)
        }

        soft_exercise.setOnClickListener {
            startActivity(TrainingModel.Mode.SLOW, user)
        }
    }

    private fun startActivity(mode: TrainingModel.Mode, user: User?){
        activeMode = when(mode) {
            TrainingModel.Mode.SLOW -> {
                slowEasy
            }
            TrainingModel.Mode.FAST -> {
                fastEasy
            }
        }
        startActivity(Intent(this, HostMainActivity::class.java).putExtra(FIREBASE_USER, user))
        finish()
    }

}