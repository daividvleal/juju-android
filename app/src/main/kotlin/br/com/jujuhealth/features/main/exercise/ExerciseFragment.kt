package br.com.jujuhealth.features.main.exercise

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.jujuhealth.R
import br.com.jujuhealth.activeMode
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.extension.getFormattedKey
import br.com.jujuhealth.features.main.HostMainActivity
import kotlinx.android.synthetic.main.fragment_exercise.*
import org.koin.android.ext.android.inject
import java.util.*

class ExerciseFragment : Fragment(R.layout.fragment_exercise) {

    private val exerciseViewModel: ExerciseViewModel by inject()
    private lateinit var activityHost: HostMainActivity
    private var progressMax = false

    override fun onResume() {
        exerciseViewModel.resetFields()
        exerciseViewModel.getTrainingDiary(Calendar.getInstance().getFormattedKey())
        startFields()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityHost = (requireActivity() as HostMainActivity)
        activityHost.setUpToolbarWithMenuItem(
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

        exerciseViewModel.diary.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> { }
                BaseModel.Status.SUCCESS -> {
                    meta.setSeries(it.data?.getSeries())
                }
                BaseModel.Status.ERROR -> {
                    meta.setSeries(0)
                }
                BaseModel.Status.DEFAULT -> {
                    meta.setSeries(0)
                }
            }
        })

        exerciseViewModel.counter.observe(viewLifecycleOwner, Observer {
            time_counter.text = it
        })

        exerciseViewModel.whatDoing.observe(viewLifecycleOwner, Observer {
            if(it != -1){
                what_doing.text = resources.getString(it)
            }
        })

        exerciseViewModel.meta.observe(viewLifecycleOwner, Observer {
            meta.setMeta(it)
        })

        exerciseViewModel.progress.observe(viewLifecycleOwner, Observer {
            if (!progressMax){
                progress.progress = it
            }
            if(it >= progress.max){
                activityHost.setExerciseFinished(true)
                activityHost.addSeries()
                exerciseViewModel.addSeries()
                exerciseViewModel.progress.value = 0
                progressMax = true
            }
        })

        exerciseViewModel.series.observe(viewLifecycleOwner, Observer {
            meta.setSeries(it)
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
        if(activityHost.isExerciseFinished()){
            activityHost.goToCalendar()
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
