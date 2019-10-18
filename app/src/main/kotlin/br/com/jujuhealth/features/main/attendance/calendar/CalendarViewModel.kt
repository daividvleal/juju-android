package br.com.jujuhealth.features.main.attendance.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.data.request.calendar.ServiceCalendarContract
import org.koin.core.KoinComponent

class CalendarViewModel(private val serviceCalendarContract: ServiceCalendarContract) : ViewModel(),
    KoinComponent {

    val collectionAll = MutableLiveData<BaseModel<List<TrainingDiary>, Exception>>()
    val collectionDiary = MutableLiveData<BaseModel<List<TrainingDiary>, Exception>>()
    val diary = MutableLiveData<BaseModel<TrainingDiary, Exception>>()
    val successInserted = MutableLiveData<BaseModel<Boolean, Exception>>()

    fun insertTraining(
        uid: String?,
        date: String,
        trainingDiary: TrainingDiary
    ) {
        uid?.let {
            successInserted.value?.status = BaseModel.Status.LOADING
            serviceCalendarContract.insertTrainingDiary(uid, date, trainingDiary, {
                successInserted.value = BaseModel(BaseModel.Status.SUCCESS, true, null)
            }, {
                successInserted.value = BaseModel(BaseModel.Status.ERROR, false, it)
            })
        } ?: run {
            successInserted.value = BaseModel(BaseModel.Status.ERROR, false, Exception())
        }

    }

    fun getTrainingDiary(
        uid: String?,
        date: String
    ) {
        uid?.let {
            diary.value?.status = BaseModel.Status.LOADING
            serviceCalendarContract.getTrainingDiaryDay(uid, date, {
                diary.value = BaseModel(BaseModel.Status.SUCCESS, it, null)
            }, {
                diary.value = BaseModel(BaseModel.Status.ERROR, null, it)
            })
        } ?: run {
            diary.value = BaseModel(BaseModel.Status.ERROR, null, Exception())
        }

    }

    fun getTrainingRange(
        uid: String?,
        startDate: String,
        endDate: String
    ) {
        uid?.let {
            collectionDiary.value?.status = BaseModel.Status.LOADING
            serviceCalendarContract.getTrainingDiaryRange(uid, startDate, endDate, {
                collectionDiary.value = BaseModel(BaseModel.Status.SUCCESS, it, null)
            }, {
                collectionDiary.value = BaseModel(BaseModel.Status.ERROR, null, it)
            })
        } ?: run {
            collectionDiary.value = BaseModel(BaseModel.Status.ERROR, null, Exception())
        }

    }

    fun getTrainingAll(uid: String?) {
        uid?.let {
            collectionAll.value?.status = BaseModel.Status.LOADING
            serviceCalendarContract.getTrainingAll(uid, {
                collectionAll.value = BaseModel(BaseModel.Status.SUCCESS, it, null)
            }, {
                collectionAll.value = BaseModel(BaseModel.Status.ERROR, null, it)
            })
        } ?: run {
            collectionDiary.value = BaseModel(BaseModel.Status.ERROR, null, java.lang.Exception())
        }
    }
}