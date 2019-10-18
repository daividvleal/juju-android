package br.com.jujuhealth.features.main.exercise.filter

import androidx.lifecycle.ViewModel
import br.com.jujuhealth.*
import br.com.jujuhealth.data.model.TrainingModel
import br.com.jujuhealth.widget.CustomLevel
import org.koin.core.KoinComponent

class LevelViewModel: ViewModel(), KoinComponent {

    fun setActivatedMode(level: CustomLevel.LEVEL) {
        activeMode = if (activeMode?.mode == TrainingModel.Mode.FAST) {
            when (level) {
                CustomLevel.LEVEL.MEDIUM -> {
                    fastMedium.copy()
                }
                CustomLevel.LEVEL.EASY -> {
                    fastEasy.copy()
                }
                CustomLevel.LEVEL.HARD -> {
                    fastHard.copy()
                }
            }
        } else {
            when (level) {
                CustomLevel.LEVEL.MEDIUM -> {
                    slowMedium.copy()
                }
                CustomLevel.LEVEL.EASY -> {
                    slowEasy.copy()
                }
                CustomLevel.LEVEL.HARD -> {
                    slowHard.copy()
                }
            }
        }
    }

}