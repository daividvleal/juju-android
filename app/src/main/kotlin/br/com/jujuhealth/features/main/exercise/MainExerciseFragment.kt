package br.com.jujuhealth.features.main.exercise

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.jujuhealth.R
import br.com.jujuhealth.activeMode
import br.com.jujuhealth.data.model.TrainingModel
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_FAST_TRAIN
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_SLOW_TRAIN
import br.com.jujuhealth.fastEasy
import br.com.jujuhealth.features.main.HostMainActivity
import br.com.jujuhealth.features.main.MainViewModel
import br.com.jujuhealth.slowEasy
import kotlinx.android.synthetic.main.fragment_main_exercise.*
import org.koin.android.ext.android.inject

class MainExerciseFragment : Fragment(R.layout.fragment_main_exercise){

    private val mainViewModel: MainViewModel by inject()
    private lateinit var activityHost: HostMainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityHost = (activity as HostMainActivity)
        hard_exercise.setOnClickListener {
            mainViewModel.log(FIREBASE_EVENT_PRESSED_FAST_TRAIN)
            navigateToExercise(TrainingModel.Mode.FAST)
        }

        soft_exercise.setOnClickListener {
            mainViewModel.log(FIREBASE_EVENT_PRESSED_SLOW_TRAIN)
            navigateToExercise(TrainingModel.Mode.SLOW)
        }
    }

    private fun navigateToExercise(mode: TrainingModel.Mode){
        activeMode = when(mode) {
            TrainingModel.Mode.SLOW -> {
                slowEasy
            }
            TrainingModel.Mode.FAST -> {
                fastEasy
            }
        }
        activityHost.findNavController().navigate(R.id.go_to_exercise)
    }

}