package br.com.jujuhealth.data.request.calendar

import br.com.jujuhealth.data.model.TrainingDiary

interface ServiceCalendarContract {

    fun getTrainingDiaryDay(uid: String, date: String, success: (TrainingDiary?) -> Unit, error: (Exception?) -> Unit)

    fun getTrainingDiaryRange(uid: String, startDate: String, endDate: String, success: (List<TrainingDiary>?) -> Unit, error: (Exception?) -> Unit)

    fun getTrainingAll(uid: String, success: (List<TrainingDiary>?) -> Unit, error: (Exception?) -> Unit)

    fun insertTrainingDiary(uid: String, date: String, trainingDiary: TrainingDiary, success: () -> Unit, error: (Exception?) -> Unit)

}