package br.com.jujuhealth.features.main.exercise.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.jujuhealth.R
import br.com.jujuhealth.features.main.HostMainActivity
import br.com.jujuhealth.widget.CustomLevel
import kotlinx.android.synthetic.main.fragment_level.*
import org.koin.android.ext.android.inject

class LevelFragment : Fragment(R.layout.fragment_level) {

    private val levelViewModel: LevelViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HostMainActivity).setUpToolbarTitle(R.string.exercise_level)

        back.setOnClickListener {
            activity?.onBackPressed()
        }

        easy.setOnClickListener {
            CustomLevel.setSelectedLevel(
                easy, CustomLevel.LEVEL.EASY,
                hard, CustomLevel.LEVEL.HARD,
                medium, CustomLevel.LEVEL.MEDIUM
            )
            levelViewModel.setActivatedMode(CustomLevel.LEVEL.EASY)
        }

        medium.setOnClickListener {
            CustomLevel.setSelectedLevel(
                medium, CustomLevel.LEVEL.MEDIUM,
                easy, CustomLevel.LEVEL.EASY,
                hard, CustomLevel.LEVEL.HARD
            )
            levelViewModel.setActivatedMode(CustomLevel.LEVEL.MEDIUM)
        }

        hard.setOnClickListener {
            CustomLevel.setSelectedLevel(
                hard, CustomLevel.LEVEL.HARD,
                medium, CustomLevel.LEVEL.MEDIUM,
                easy, CustomLevel.LEVEL.EASY
            )
            levelViewModel.setActivatedMode(CustomLevel.LEVEL.HARD)
        }

    }
}