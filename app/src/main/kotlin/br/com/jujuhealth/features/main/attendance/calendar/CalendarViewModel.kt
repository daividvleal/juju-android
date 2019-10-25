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
    val diaryOnCalendar = MutableLiveData<BaseModel<TrainingDiary, Exception>>()
    val successInserted = MutableLiveData<BaseModel<Boolean, Exception>>()

    fun insertTraining(
        date: String,
        trainingDiary: TrainingDiary?
    ) {
        successInserted.value = BaseModel(BaseModel.Status.LOADING, null, null)
        trainingDiary?.let { itTrainingDiary ->
            serviceCalendarContract.insertTrainingDiary(date, itTrainingDiary, {
                successInserted.value = BaseModel(BaseModel.Status.SUCCESS, true, null)
            }, {
                successInserted.value = BaseModel(BaseModel.Status.SUCCESS, false, it)
            })
        } ?: run {
            successInserted.value = BaseModel(BaseModel.Status.ERROR, false, Exception())
        }
    }


    fun getTrainingDiary(
        date: String,
        mutableDiary: MutableLiveData<BaseModel<TrainingDiary, Exception>> = diary
    ) {
        mutableDiary.value = BaseModel(BaseModel.Status.LOADING, null, null)
        serviceCalendarContract.getTrainingDiaryDay(date, {
            it?.let {
                mutableDiary.value = BaseModel(BaseModel.Status.SUCCESS, it, null)
            } ?: run {
                mutableDiary.value = BaseModel(BaseModel.Status.DEFAULT, null, null)
            }
        }, {
            mutableDiary.value = BaseModel(BaseModel.Status.ERROR, null, it)
        })
    }

    fun getTrainingRange(
        startDate: String,
        endDate: String
    ) {
        collectionDiary.value = BaseModel(BaseModel.Status.LOADING, null, null)
        serviceCalendarContract.getTrainingDiaryRange(startDate, endDate, {
            collectionDiary.value = BaseModel(BaseModel.Status.SUCCESS, it, null)
        }, {
            collectionDiary.value = BaseModel(BaseModel.Status.ERROR, null, it)
        })
    }

    fun getDiaryOnCalendar(date: String){
        getTrainingDiary(date, diaryOnCalendar)
    }

    fun getActualMonth() {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        getTrainingRange(
            "${year}-${month}-01",
            "${year}-${month}-31"
        )
    }
}