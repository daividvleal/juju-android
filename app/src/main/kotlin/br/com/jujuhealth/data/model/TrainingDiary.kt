package br.com.jujuhealth.data.model

import java.io.Serializable

data class TrainingDiary (
    var date: String? = "",
    var seriesSlowEasy: Int = 0,
    var seriesSlowMedium: Int = 0,
    var seriesSlowHard: Int = 0,
    var seriesFastEasy: Int = 0,
    var seriesFastMedium: Int = 0,
    var seriesFastHard: Int = 0
): Serializable