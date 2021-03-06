package br.com.jujuhealth.features.main.exercise

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jujuhealth.R
import br.com.jujuhealth.activeMode
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.data.request.calendar.ServiceCalendarContract
import org.koin.core.KoinComponent

class ExerciseViewModel(private val serviceCalendarContract: ServiceCalendarContract) : ViewModel(),
    KoinComponent {

    var doAgain: Boolean = false
    var count = 0
    var currentAnimator: Animator? = null
    var countDownTimer: CountDownTimer? = null
    var animationEnd = false

    val counter = MutableLiveData<String>()
    val whatDoing = MutableLiveData<Int>()
    val meta = MutableLiveData<String>()
    val progress = MutableLiveData<Int>()
    val series = MutableLiveData<Int>()
    val diary = MutableLiveData<BaseModel<TrainingDiary, Exception>>()

    fun resetFields() {
        animationEnd = false
        doAgain = false
        count = 0
        currentAnimator = null
        countDownTimer = null

        counter.value = ""
        whatDoing.value = -1
        meta.value = ""
        progress.value = 0
    }

    private fun resetAnimationAndCount() {
        currentAnimator = null
        countDownTimer?.cancel()
        whatDoing.value = -1
    }

    fun contract(
        thumbView: View,
        imageResId: Int,
        expanded_image: ImageView,
        container: FrameLayout,
        progressMax: Int,
        finish: () -> Unit
    ) {
        currentAnimator?.cancel()
        countDownTimer?.cancel()
        expanded_image.setImageResource(imageResId)

        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        thumbView.getGlobalVisibleRect(startBoundsInt)
        container.getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        thumbView.alpha = 0f
        expanded_image.visibility = View.VISIBLE

        currentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    expanded_image,
                    View.X,
                    startBounds.left,
                    finalBounds.left
                )
            ).apply {
                with(
                    ObjectAnimator.ofFloat(
                        expanded_image,
                        View.Y,
                        startBounds.top,
                        finalBounds.top
                    )
                )
                with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_X, startScale, 0.4f))
                with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_Y, startScale, 0.4f))
            }
            duration = activeMode?.contractionDuration!!
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationStart(animation: Animator?) {
                    counter.value = (activeMode?.contractionDuration!! / 1000).toString()
                    setCountDown(activeMode?.contractionDuration!!)
                    whatDoing.value = R.string.contract
                    super.onAnimationStart(animation)
                    countDownTimer?.start()
                }

                override fun onAnimationEnd(animation: Animator) {
                    resetAnimationAndCount()
                    relax(
                        thumbView,
                        startBounds,
                        startScale,
                        imageResId,
                        expanded_image,
                        container,
                        progressMax,
                        finish
                    )
                }

                override fun onAnimationCancel(animation: Animator) {
                    resetAnimationAndCount()
                }

            })
            start()
        }
    }

    private fun relax(
        thumbView: View,
        startBounds: RectF,
        startScale: Float,
        imageResId: Int,
        expanded_image: ImageView,
        container: FrameLayout,
        progressMax: Int,
        finish: () -> Unit
    ) {
        currentAnimator?.cancel()
        countDownTimer?.cancel()

        currentAnimator = AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(expanded_image, View.X, startBounds.left)).apply {
                with(ObjectAnimator.ofFloat(expanded_image, View.Y, startBounds.top))
                with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_X, startScale))
                with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_Y, startScale))
            }
            duration = activeMode?.relaxDuration!!
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationStart(animation: Animator?) {
                    counter.value = (activeMode?.relaxDuration!! / 1000).toString()
                    setCountDown(activeMode?.relaxDuration!!)
                    whatDoing.value = R.string.relaxe
                    super.onAnimationStart(animation)
                    countDownTimer?.start()
                }

                override fun onAnimationEnd(animation: Animator) {
                    if(animationEnd && Build.VERSION_CODES.O > Build.VERSION.SDK_INT){
                        animationEnd = false
                    }else{
                        animationEnd = true
                        thumbView.alpha = 1f
                        expanded_image.visibility = View.GONE
                        count++
                        if (doAgain && count < activeMode?.repetitions!!) {
                            meta.value = "$count/${activeMode?.repetitions!!}"
                            progress.value = progress.value?.plus(1)
                            contract(thumbView, imageResId, expanded_image, container, progressMax, finish)
                        }else if(doAgain && count == activeMode?.repetitions!!){
                            meta.value = "$count/${activeMode?.repetitions!!}"
                            progress.value = progress.value?.plus(1)
                            addSeries()
                            finish()
                        }
                    }
                }

                override fun onAnimationCancel(animation: Animator) {
                    resetAnimationAndCount()
                    thumbView.alpha = 1f
                    expanded_image.visibility = View.GONE
                }
            })
            start()
        }
    }

    private fun setCountDown(duration: Long) {
        countDownTimer = object : CountDownTimer(duration, 1000) {

            fun setTime(millisUntilFinished: Long) {
                if (Build.VERSION_CODES.O > Build.VERSION.SDK_INT && millisUntilFinished <= 2000L) {
                    counter.value = "2"
                    Handler().postDelayed({
                        counter.value = "1"
                    }, 800)
                }
                counter.value =  ((millisUntilFinished + 1000) / 1000).toString()
            }

            override fun onTick(millisUntilFinished: Long) {
                setTime(millisUntilFinished)
            }

            override fun onFinish() { }

        }
    }

    fun getTrainingDiary(
        date: String
    ) {
        diary.value = BaseModel(BaseModel.Status.LOADING, null, null)
        serviceCalendarContract.getTrainingDiaryDay(date, {
            it?.let {
                series.value = it.returnSpecifySerieFromMode(activeMode)
                diary.value = BaseModel(BaseModel.Status.SUCCESS, it, null)
            } ?: run {
                diary.value = BaseModel(BaseModel.Status.DEFAULT, null, null)
            }
        }, {
            diary.value = BaseModel(BaseModel.Status.ERROR, null, it)
        })
    }

    fun addSeries() {
        series.value?.let {
            series.value = series.value?.plus(1)
        } ?: run {
            series.value = 1
        }
    }

}