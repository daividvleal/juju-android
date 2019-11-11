package br.com.jujuhealth.features.main.exercise

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.jujuhealth.R
import br.com.jujuhealth.activeMode
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.TrainingModel
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_RESUME_EXERCISE
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_START_EXERCISE
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_STOP_EXERCISE
import br.com.jujuhealth.extension.getFormattedKey
import br.com.jujuhealth.features.main.HostMainActivity
import br.com.jujuhealth.features.main.exercise.animator.ProgressBarAnimation
import kotlinx.android.synthetic.main.fragment_exercise.*
import org.koin.android.ext.android.inject
import java.util.*

class ExerciseFragment : Fragment(R.layout.fragment_exercise) {

    private val exerciseViewModel: ExerciseViewModel by inject()
    private lateinit var activityHost: HostMainActivity

    override fun onResume() {
        exerciseViewModel.resetFields()
        exerciseViewModel.getTrainingDiary(Calendar.getInstance().getFormattedKey())
        activityHost.log(FIREBASE_EVENT_PRESSED_RESUME_EXERCISE)
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
        activityHost.setNavigationIcon(R.drawable.ic_arrow_back){
            activityHost.findNavController().navigateUp()
        }

        when(activeMode?.difficulty){
            TrainingModel.Difficulty.EASY -> {
                btn_start.setEndText(getString(R.string.level, getString(R.string.level_easy)))
            }
            TrainingModel.Difficulty.MEDIUM -> {
                btn_start.setEndText(getString(R.string.level, getString(R.string.level_medium)))
            }
            TrainingModel.Difficulty.HARD -> {
                btn_start.setEndText(getString(R.string.level, getString(R.string.level_hard)))
            }
        }

        btn_start.setOnClick {
            flipVisibility()
            start()
        }


        btn_play.setOnClickListener {
            activityHost.log(FIREBASE_EVENT_PRESSED_START_EXERCISE)
            start()
        }

        btn_stop.setOnClickListener {
            activityHost.log(FIREBASE_EVENT_PRESSED_STOP_EXERCISE)
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
                    val series = it.data?.returnSpecifySerieFromMode(activeMode)
                    meta.setSeries(series)
                    activityHost.setSeries(series)
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
            animateProgressBar(progress.progress, it)
        })

        exerciseViewModel.series.observe(viewLifecycleOwner, Observer {
            activityHost.setSeries(it)
            meta.setSeries(it)
        })
    }

    private fun animateProgressBar(start: Int, end: Int){
        val anim = ProgressBarAnimation(progress, start, end)
        anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                if(end == (progress.max/100)){
                    animationProgressBarToReset()
                }
            }
            override fun onAnimationStart(animation: Animation?) { }

        })
        anim.duration = 400
        progress.startAnimation(anim)
    }

    private fun animationProgressBarToReset(){
        val anim = ProgressBarAnimation(progress, progress.max, 0)
        anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) { }
            override fun onAnimationEnd(animation: Animation?) {
                progress.progress = 0
                exerciseViewModel.progress.value = 0
                activityHost.setExerciseFinished(true)
                activityHost.goToCalendar()
            }
            override fun onAnimationStart(animation: Animation?) {}
        })
        anim.duration = 250
        progress.startAnimation(anim)
    }

    private fun start() {
        btn_play.visibility = View.GONE
        btn_stop.visibility = View.VISIBLE
        exerciseViewModel.doAgain = true
        exerciseViewModel.contract(circle, R.drawable.ic_exercicio_animation_complete, expanded_image, container, activeMode?.repetitions!!, ::cancel)
    }

    private fun cancel() {
        exerciseViewModel.animationEnd = false
        exerciseViewModel.doAgain = false
        exerciseViewModel.currentAnimator?.cancel()
        exerciseViewModel.countDownTimer?.cancel()
        btn_play.visibility = View.VISIBLE
        btn_stop.visibility = View.GONE
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
        progress.max = activeMode?.repetitions!!*100
    }

}
