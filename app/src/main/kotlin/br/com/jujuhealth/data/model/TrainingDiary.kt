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

    fun getSeries() =
        seriesSlowEasy + seriesSlowMedium + seriesSlowHard + seriesFastEasy + seriesFastMedium + seriesFastHard


    fun addTraining(trainingModel: TrainingModel?, qtd: Int): TrainingDiary {
        return trainingModel?.let {
            when (trainingModel.mode) {
                TrainingModel.Mode.SLOW -> {
                    when (trainingModel.difficulty) {
                        TrainingModel.Difficulty.EASY -> {
                            seriesSlowEasy += qtd
                        }
                        TrainingModel.Difficulty.MEDIUM -> {
                            seriesSlowMedium += qtd
                        }
                        TrainingModel.Difficulty.HARD -> {
                            seriesSlowHard += qtd
                        }
                    }
                }
                TrainingModel.Mode.FAST -> {
                    when (trainingModel.difficulty) {
                        TrainingModel.Difficulty.EASY -> {
                            seriesFastEasy += qtd
                        }
                        TrainingModel.Difficulty.MEDIUM -> {
                            seriesFastMedium += qtd
                        }
                        TrainingModel.Difficulty.HARD -> {
                            seriesFastHard += qtd
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

}