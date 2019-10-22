package br.com.jujuhealth.features.main.exercise

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.jujuhealth.R
import br.com.jujuhealth.activeMode
import br.com.jujuhealth.features.main.HostMainActivity
import kotlinx.android.synthetic.main.fragment_exercise.*
import org.koin.android.ext.android.inject
import androidx.lifecycle.Observer

class ExerciseFragment : Fragment(R.layout.fragment_exercise) {

    private val exerciseViewModel: ExerciseViewModel by inject()

    override fun onResume() {
        exerciseViewModel.resetFields()
        startFields()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HostMainActivity).setUpToolbarWithMenuItem(
            R.string.exercise,
            R.menu.toolbar_exercise_menu
        )

        btn_start.setOnClick {
            flipVisibility()
        }

        btn_play.setOnClickListener {
            start()
        }

        btn_stop.setOnClickListener {
            cancel()
        }

        startFields()
        setObservable()
    }

    private fun setObservable(){
        exerciseViewModel.counter.observe(this, Observer {
            time_counter.text = it
        })

        exerciseViewModel.whatDoing.observe(this, Observer {
            if(it != -1){
                what_doing.text = resources.getString(it)
            }
        })

        exerciseViewModel.meta.observe(this, Observer {
            meta.setMeta(it)
        })

        exerciseViewModel.progress.observe(this, Observer {
            if(it >= progress.max){
                (requireActivity() as HostMainActivity).setExerciseFinished(true)
            }
            progress.progress = it
        })
    }

    private fun start() {
        btn_play.visibility = View.GONE
        btn_stop.visibility = View.VISIBLE
        exerciseViewModel.doAgain = true
        exerciseViewModel.contract(circle, R.drawable.ic_exercicio_animation_complete, expanded_image, container)
    }

    private fun cancel() {
        exerciseViewModel.doAgain = false
        exerciseViewModel.currentAnimator?.cancel()
        exerciseViewModel.countDownTimer?.cancel()
        btn_play.visibility = View.VISIBLE
        btn_stop.visibility = View.GONE
        if((requireActivity() as HostMainActivity).isExerciseFinished()){
            (requireActivity() as HostMainActivity).findNavController().navigate(R.id.go_to_attendance)
        }
    }

    private fun flipVisibility() {
        if (group_start.visibility == View.VISIBLE) {
            group_start.visibility = View.GONE
            group_doing.visibility = View.VISIBLE
        } else {
            group_start.visibility = View.VISIBLE
            group_doing.visibility = View.GONE
            btn_stop.visibility = View.GONE
        }
    }

    private fun startFields() {
        time_counter.text = (activeMode?.contractionDuration!! / 1000).toString()
        exerciseViewModel.progress.value = 0
        exerciseViewModel.count = 0
        meta.setMeta("0/${activeMode?.repetitions}")
        what_doing.text = ""
        progress.progress = 0
        progress.max = activeMode?.repetitions!!
    }

}
