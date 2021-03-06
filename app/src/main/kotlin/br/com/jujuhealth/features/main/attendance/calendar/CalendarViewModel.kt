package br.com.jujuhealth.features.main.attendance.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.data.request.calendar.ServiceCalendarContract
import br.com.jujuhealth.extension.getFormattedKey
import org.koin.core.KoinComponent
import java.util.*

class CalendarViewModel(private val serviceCalendarContract: ServiceCalendarContract) : ViewModel(),
    KoinComponent {

    val collectionDiary = MutableLiveData<BaseModel<List<TrainingDiary>, Exception>>()
    val diary = MutableLiveData<BaseModel<TrainingDiary, Exception>>()
    val diaryOnCalendar = MutableLiveData<BaseModel<TrainingDiary, Exception>>()
    val successInserted = MutableLiveData<BaseModel<Boolean, Exception>>()
    val successUpdated = MutableLiveData<BaseModel<Boolean, Exception>>()
    var calendar = Calendar.getInstance()

    init {
        calendar.time = Date()
    }

    fun updateTraining(trainingDiary: TrainingDiary?){
        successUpdated.value = BaseModel(BaseModel.Status.LOADING, null, null)
        trainingDiary?.let { itTrainingDiary ->
            val calendar = Calendar.getInstance()
            calendar.time = itTrainingDiary.date.toDate()
            serviceCalendarContract.insertTrainingDiary(calendar.getFormattedKey(), itTrainingDiary, {
                successUpdated.value = BaseModel(BaseModel.Status.SUCCESS, true, null)
            }, {
                successUpdated.value = BaseModel(BaseModel.Status.SUCCESS, false, it)
            })
        } ?: run {
            successUpdated.value = BaseModel(BaseModel.Status.ERROR, false, Exception())
        }
    }

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

    private fun getTrainingRange(
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

    fun getForwardMonth() {
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        if(calendar.get(Calendar.MONTH) == 11){
            calendar.time = Date((year-1900)+1, 0, 1)
        }else{
            calendar.set(year, month+1, 1)
        }
        getActualMonth()
    }

    fun getPreviousMonth(){
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        if(calendar.get(Calendar.MONTH) == 0){
            calendar.time = Date(year-(1+1900), 11, 1)
        }else{
            calendar.set(year, month-1, 1)
        }
        getActualMonth()
    }

    fun getDiaryOnCalendar(date: String){
        getTrainingDiary(date, diaryOnCalendar)
    }

    fun getActualMonth() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        if(month < 10){
            getTrainingRange(
                "${year}-0${month}-01",
                "${year}-0${month}-31"
            )
        }else{
            getTrainingRange(
                "${year}-${month}-01",
                "${year}-${month}-31"
            )
        }
    }
}