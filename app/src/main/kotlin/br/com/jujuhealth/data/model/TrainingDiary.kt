package br.com.jujuhealth.data.model

import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.*

data class TrainingDiary(
    var date: Timestamp = Timestamp(Calendar.getInstance().time),
    var seriesSlowEasy: Int = 0,
    var seriesSlowMedium: Int = 0,
    var seriesSlowHard: Int = 0,
    var seriesFastEasy: Int = 0,
    var seriesFastMedium: Int = 0,
    var seriesFastHard: Int = 0,
    var urineLoss: Int = 0
) : Serializable {

    fun hasExercise(): Boolean {
        if (seriesSlowEasy + seriesSlowMedium + seriesSlowHard + seriesFastEasy + seriesFastMedium + seriesFastHard > 0) {
            return true
        }
        return false
    }

    fun addTraining(trainingModel: TrainingModel?): TrainingDiary{
        return trainingModel?.let {
            when (trainingModel.mode) {
                TrainingModel.Mode.SLOW -> {
                    when (trainingModel.difficulty) {
                        TrainingModel.Difficulty.EASY -> {
                            seriesSlowEasy++
                        }
                        TrainingModel.Difficulty.MEDIUM -> {
                            seriesSlowMedium++
                        }
                        TrainingModel.Difficulty.HARD -> {
                            seriesSlowHard++
                        }
                    }
                }
                TrainingModel.Mode.FAST -> {
                    when (trainingModel.difficulty) {
                        TrainingModel.Difficulty.EASY -> {
                            seriesFastEasy++
                        }
                        TrainingModel.Difficulty.MEDIUM -> {
                            seriesFastMedium++
                        }
                        TrainingModel.Difficulty.HARD -> {
                            seriesFastHard++
                        }
                    }
                }
            }
            this
        } ?: run {
            TrainingDiary()
        }
    }

    fun hasUrineLoss() : Boolean{
        return when(urineLoss){
            0 -> false
            else -> true

        }
    }

    fun setLoss(urineLoss: UrineLoss){
        when(urineLoss){
            UrineLoss.NONE -> {}
            UrineLoss.LOW -> {}
            UrineLoss.MODERATE -> {}
            UrineLoss.HIGH -> {}
        }
    }

    fun getLoss(){
        when(urineLoss){
            0 -> {UrineLoss.NONE}
            1 -> {UrineLoss.LOW}
            2 -> {UrineLoss.MODERATE}
            3 -> {UrineLoss.HIGH}
        }
    }

    enum class UrineLoss {
        NONE, LOW, MODERATE, HIGH
    }
}