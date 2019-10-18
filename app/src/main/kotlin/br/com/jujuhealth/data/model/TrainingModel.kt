package br.com.jujuhealth.data.model

import java.io.Serializable

data class TrainingModel (
    val mode: Mode = Mode.SLOW,
    val difficulty: Difficulty = Difficulty.EASY,
    val repetitions: Int = 2,
    val contractionDuration: Long = 4000,
    val relaxDuration: Long = 4000
) : Serializable {

    enum class Mode {
        SLOW, FAST
    }

    enum class Difficulty {
        EASY, MEDIUM, HARD
    }

}