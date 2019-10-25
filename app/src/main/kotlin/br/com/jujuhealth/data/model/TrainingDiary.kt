package br.com.jujuhealth.data.model

import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class TrainingDiary(
    var date: Timestamp = Timestamp(Calendar.getInstance().time),
    var seriesSlowEasy: Int = 0,
    var seriesSlowMedium: Int = 0,
    var seriesSlowHard: Int = 0,
    var seriesFastEasy: Int = 0,
    var seriesFastMedium: Int = 0,
    var seriesFastHard: Int = 0,
    var urineLoss: ArrayList<Int> = ArrayList()
) : Serializable {

    fun hasExercise(): Boolean {
        if (seriesSlowEasy + seriesSlowMedium + seriesSlowHard + seriesFastEasy + seriesFastMedium + seriesFastHard > 0) {
            return true
        }
        return false
    }

    fun getSeries() = seriesSlowEasy + seriesSlowMedium + seriesSlowHard + seriesFastEasy + seriesFastMedium + seriesFastHard


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

    fun hasUrineLoss() = urineLoss.size > 0

    fun getUrineLossSize() = urineLoss.size

    enum class UrineLoss {
        NONE, LOW, MODERATE, HIGH
    }
}