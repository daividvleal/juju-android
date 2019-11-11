package br.com.jujuhealth.features.main.exercise.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.jujuhealth.R
import br.com.jujuhealth.activeMode
import br.com.jujuhealth.data.model.TrainingModel
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_EASY_LEVEL
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_HARD_LEVEL
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_MEDIUM_LEVEL
import br.com.jujuhealth.features.main.HostMainActivity
import br.com.jujuhealth.widget.CustomLevel
import kotlinx.android.synthetic.main.fragment_level.*
import org.koin.android.ext.android.inject

class LevelFragment : Fragment(R.layout.fragment_level) {

    private val levelViewModel: LevelViewModel by inject()
    private lateinit var activityHost: HostMainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityHost = (activity as HostMainActivity)
        activityHost.setUpToolbarTitle(R.string.exercise_level)

        back.setOnClickListener {
            activityHost.onBackPressed()
        }

        when(activeMode?.difficulty){
            TrainingModel.Difficulty.EASY -> {
                CustomLevel.setSelectedLevel(
                    easy, CustomLevel.LEVEL.EASY,
                    hard, CustomLevel.LEVEL.HARD,
                    medium, CustomLevel.LEVEL.MEDIUM,
                    activeMode?.repetitions ?: 0
                )
            }
            TrainingModel.Difficulty.MEDIUM -> {
                CustomLevel.setSelectedLevel(
                    medium, CustomLevel.LEVEL.MEDIUM,
                    easy, CustomLevel.LEVEL.EASY,
                    hard, CustomLevel.LEVEL.HARD,
                    activeMode?.repetitions ?: 0
                )
            }
            TrainingModel.Difficulty.HARD -> {
                CustomLevel.setSelectedLevel(
                    hard, CustomLevel.LEVEL.HARD,
                    medium, CustomLevel.LEVEL.MEDIUM,
                    easy, CustomLevel.LEVEL.EASY,
                    activeMode?.repetitions ?: 0
                )
            }
        }

        easy.setOnClickListener {
            activityHost.setSeries(0)
            activityHost.log(FIREBASE_EVENT_PRESSED_EASY_LEVEL)
            levelViewModel.setActivatedMode(CustomLevel.LEVEL.EASY)
            CustomLevel.setSelectedLevel(
                easy, CustomLevel.LEVEL.EASY,
                hard, CustomLevel.LEVEL.HARD,
                medium, CustomLevel.LEVEL.MEDIUM,
                activeMode?.repetitions ?: 0
            )
        }

        medium.setOnClickListener {
            activityHost.setSeries(0)
            activityHost.log(FIREBASE_EVENT_PRESSED_MEDIUM_LEVEL)
            levelViewModel.setActivatedMode(CustomLevel.LEVEL.MEDIUM)
            CustomLevel.setSelectedLevel(
                medium, CustomLevel.LEVEL.MEDIUM,
                easy, CustomLevel.LEVEL.EASY,
                hard, CustomLevel.LEVEL.HARD,
                activeMode?.repetitions ?: 0
            )
        }

        hard.setOnClickListener {
            activityHost.setSeries(0)
            activityHost.log(FIREBASE_EVENT_PRESSED_HARD_LEVEL)
            levelViewModel.setActivatedMode(CustomLevel.LEVEL.HARD)
            CustomLevel.setSelectedLevel(
                hard, CustomLevel.LEVEL.HARD,
                medium, CustomLevel.LEVEL.MEDIUM,
                easy, CustomLevel.LEVEL.EASY,
                activeMode?.repetitions ?: 0
            )
        }

    }
}