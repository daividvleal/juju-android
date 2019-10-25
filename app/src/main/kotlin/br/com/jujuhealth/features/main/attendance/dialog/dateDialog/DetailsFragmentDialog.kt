package br.com.jujuhealth.features.main.attendance.dialog.dateDialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.TrainingDiary
import kotlinx.android.synthetic.main.fragment_series.*

class DetailsFragmentDialog(private val trainingDiary: TrainingDiary?): Fragment(R.layout.fragment_series){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        slow_easy_text.text = getString(R.string.easy, trainingDiary?.seriesSlowEasy ?: 0)
        slow_medium_text.text = getString(R.string.medium, trainingDiary?.seriesSlowMedium ?: 0)
        slow_hard_text.text = getString(R.string.hard, trainingDiary?.seriesSlowHard ?: 0)

        fast_easy_text.text = getString(R.string.easy, trainingDiary?.seriesFastEasy ?: 0)
        fast_medium_text.text = getString(R.string.medium, trainingDiary?.seriesFastMedium ?: 0)
        fast_hard_text.text = getString(R.string.hard, trainingDiary?.seriesFastHard ?: 0)
    }

}