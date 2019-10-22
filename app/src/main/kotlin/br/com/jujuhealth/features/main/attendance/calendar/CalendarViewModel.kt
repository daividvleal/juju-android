package br.com.jujuhealth.features.main.attendance.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.data.request.calendar.ServiceCalendarContract
import org.koin.core.KoinComponent
import java.util.*

class CalendarViewModel(private val serviceCalendarContract: ServiceCalendarContract) : ViewModel(),
    KoinComponent {

    val collectionDiary = MutableLiveData<BaseModel<List<TrainingDiary>, Exception>>()
    val diary = MutableLiveData<BaseModel<TrainingDiary, Exception>>()
    val successInserted = MutableLiveData<BaseModel<Boolean, Exception>>()

    fun insertTraining(
        uid: String?,
        date: String,
        trainingDiary: TrainingDiary?
    ) {
        uid?.let {
            successInserted.value = BaseModel(BaseModel.Status.LOADING, null, null)
            trainingDiary?.let { itTrainingDiary ->
                serviceCalendarContract.insertTrainingDiary(uid, date, itTrainingDiary, {
                    successInserted.value = BaseModel(BaseModel.Status.SUCCESS, true, null)
                }, {
                    successInserted.value = BaseModel(BaseModel.Status.SUCCESS, false, it)
                })
            } ?: run {
                successInserted.value = BaseModel(BaseModel.Status.ERROR, false, Exception())
            }
        } ?: run {
            successInserted.value = BaseModel(BaseModel.Status.ERROR, false, Exception())
        }

    }

    fun getTrainingDiary(
        uid: String?,
        date: String
    ) {
        uid?.let {
            diary.value = BaseModel(BaseModel.Status.LOADING, null, null)
            serviceCalendarContract.getTrainingDiaryDay(uid, date, {
                it?.let {
                    diary.value = BaseModel(BaseModel.Status.SUCCESS, it, null)
                } ?: run {
                    diary.value = BaseModel(BaseModel.Status.DEFAULT, null, null)
                }
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
            collectionDiary.value = BaseModel(BaseModel.Status.LOADING, null, null)
            serviceCalendarContract.getTrainingDiaryRange(uid, startDate, endDate, {
                collectionDiary.value = BaseModel(BaseModel.Status.SUCCESS, it, null)
            }, {
                collectionDiary.value = BaseModel(BaseModel.Status.ERROR, null, it)
            })
        } ?: run {
            collectionDiary.value = BaseModel(BaseModel.Status.ERROR, null, Exception())
        }

    }

    fun getActualMonth(uId: String?) {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        getTrainingRange(
            uId,
            "${year}-${month}-01",
            "${year}-${month}-31"
        )
    }
}