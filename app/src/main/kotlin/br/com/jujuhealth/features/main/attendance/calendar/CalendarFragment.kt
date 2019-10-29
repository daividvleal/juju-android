package br.com.jujuhealth.features.main.attendance.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.jujuhealth.R
import br.com.jujuhealth.activeMode
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.extension.getFormattedKey
import br.com.jujuhealth.features.main.HostMainActivity
import br.com.jujuhealth.features.main.attendance.dialog.DialogDetails
import br.com.jujuhealth.features.main.attendance.dialog.DialogInsertUrineLoss
import com.applandeo.materialcalendarview.EventDay
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.koin.android.ext.android.inject
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private val viewModel: CalendarViewModel by inject()
    private var selectedDate: String = ""
    private lateinit var activityHost: HostMainActivity
    private var seeDetails = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityHost = (requireActivity() as HostMainActivity)
        setObservable()
        setUpView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getActualMonth()
    }

    private fun setUpView() {
        if (activityHost.isExerciseFinished()) {
            viewModel.getTrainingDiary(
                Calendar.getInstance().getFormattedKey()
            )
        }

        calendarView.setOnDayClickListener {
            seeDetails = true
            selectedDate = it.calendar.getFormattedKey()
            viewModel.getDiaryOnCalendar(selectedDate)
        }

        calendarView.setOnForwardPageChangeListener {
            viewModel.getForwardMonth()
        }

        calendarView.setOnPreviousPageChangeListener {
            viewModel.getPreviousMonth()
        }

        add_urine_loss.setOnClickListener {
            seeDetails = false
            viewModel.getDiaryOnCalendar(
                Calendar.getInstance().getFormattedKey()
            )
        }
    }

    private fun setObservable() {
        viewModel.successUpdated.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                    setVisibility()
                }
                BaseModel.Status.SUCCESS -> {
                    setVisibility()
                    viewModel.getActualMonth()
                }
                BaseModel.Status.ERROR -> {
                    setVisibility()
                }
                BaseModel.Status.DEFAULT -> {
                    setVisibility()
                }
            }
        })

        viewModel.diaryOnCalendar.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                    setVisibility()
                }
                BaseModel.Status.SUCCESS -> {
                    setVisibility()
                    if(seeDetails){
                        seeDetails(it.data)
                    }else{
                        addUrineLoss(it.data)
                    }
                }
                BaseModel.Status.ERROR -> {
                    setVisibility()
                    genericMessage(R.string.error_message)
                }
                BaseModel.Status.DEFAULT -> {
                    setVisibility()
                    if(seeDetails){
                        val snackBar = Snackbar.make(
                            requireView(),
                            requireContext().getString(R.string.nothing_registered, selectedDate),
                            Snackbar.LENGTH_LONG
                        )
                        snackBar.view.background =
                            requireContext().getDrawable(R.drawable.background_item_filter_dark)
                        snackBar.show()
                    }else{
                        addUrineLoss(TrainingDiary())
                    }
                }
            }
        })

        viewModel.collectionDiary.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                    setVisibility()
                }
                BaseModel.Status.SUCCESS -> {
                    val events = ArrayList<EventDay>()
                    it.data?.forEach { trainingDiary ->
                        val calendar = Calendar.getInstance()
                        calendar.time = trainingDiary.date.toDate()
                        if (trainingDiary.hasExercise() && trainingDiary.hasUrineLoss()) {
                            events.add(EventDay(calendar, R.drawable.ic_circle_drop))
                        } else if (trainingDiary.hasExercise()) {
                            events.add(EventDay(calendar, R.drawable.ic_circle_calendar))
                        } else if (trainingDiary.hasUrineLoss()) {
                            events.add(EventDay(calendar, R.drawable.ic_drop_yellow))
                        }
                    }
                    calendarView.setEvents(events)
                    setVisibility()
                }
                BaseModel.Status.ERROR -> {
                    setVisibility()
                    genericMessage(R.string.error_message)
                }
                BaseModel.Status.DEFAULT -> {
                    setVisibility()
                }
            }
        })

        viewModel.diary.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                }
                BaseModel.Status.SUCCESS -> {
                    it.data?.addTraining(activeMode, activityHost.getSeries())
                    insert(it.data)
                    viewModel.getActualMonth()
                }
                BaseModel.Status.ERROR -> {
                    genericMessage(R.string.error_message)
                }
                BaseModel.Status.DEFAULT -> {
                    var training = TrainingDiary().addTraining(activeMode, activityHost.getSeries())
                    insert(training)
                }
            }
        })

        viewModel.successInserted.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                    setVisibility()
                }
                BaseModel.Status.SUCCESS -> {
                    genericMessage(R.string.congrats)
                    setVisibility()
                    activityHost.setExerciseFinished(false)
                    viewModel.getActualMonth()
                }
                BaseModel.Status.ERROR -> {
                    setVisibility()
                    genericMessage(R.string.error_message)
                    viewModel.getActualMonth()
                }
                BaseModel.Status.DEFAULT -> {
                    setVisibility()
                }
            }
        })
    }

    private fun addUrineLoss(trainingDiary: TrainingDiary?) {
        DialogInsertUrineLoss(trainingDiary).show(
                activityHost.supportFragmentManager,
                "DIALOG_URINE_LOSS"
        )
    }

    private fun seeDetails(trainingDiary: TrainingDiary?) {
        DialogDetails(trainingDiary)
            .show(activityHost.supportFragmentManager, "DIALOG_DETAILS")
    }

    private fun insert(trainingDiary: TrainingDiary?) {
        viewModel.insertTraining(
            Calendar.getInstance().getFormattedKey(),
            trainingDiary
        )
    }

    private fun genericMessage(id: Int) {
        val snackBar = Snackbar.make(
            requireView(),
            requireContext().getString(id),
            Snackbar.LENGTH_LONG
        )
        snackBar.view.background =
            requireContext().getDrawable(R.drawable.background_item_filter_dark)
        snackBar.show()
    }

    private fun setVisibility() {
        loading.visibility = if(loading.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

}