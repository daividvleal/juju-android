package br.com.jujuhealth.data.request.calendar

import br.com.jujuhealth.data.model.TrainingDiary

interface ServiceCalendarContract {

    fun getTrainingDiaryDay(date: String, success: (TrainingDiary?) -> Unit, error: (Exception?) -> Unit)

    fun getTrainingDiaryRange(startDate: String, endDate: String, success: (List<TrainingDiary>?) -> Unit, error: (Exception?) -> Unit)

    fun getTrainingAll(success: (List<TrainingDiary>?) -> Unit, error: (Exception?) -> Unit)

    fun insertTrainingDiary(date: String, trainingDiary: TrainingDiary, success: () -> Unit, error: (Exception?) -> Unit)

    fun deleteTrainingDiary(date: String, trainingDiary: TrainingDiary, success: () -> Unit, error: (Exception?) -> Unit)

}