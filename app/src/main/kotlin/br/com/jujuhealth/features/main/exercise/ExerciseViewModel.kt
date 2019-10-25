package br.com.jujuhealth.features.main.exercise

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.CountDownTimer
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

    val counter = MutableLiveData<String>()
    val whatDoing = MutableLiveData<Int>()
    val meta = MutableLiveData<String>()
    val progress = MutableLiveData<Int>()
    val series = MutableLiveData<Int>()
    val diary = MutableLiveData<BaseModel<TrainingDiary, Exception>>()

    fun resetFields() {
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
        countDownTimer?.cancel()
        currentAnimator = null
        whatDoing.value = -1
    }

    fun contract(
        thumbView: View,
        imageResId: Int,
        expanded_image: ImageView,
        container: FrameLayout
    ) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        currentAnimator?.cancel()
        countDownTimer?.cancel()

        // Load the high-resolution "zoomed-in" image.
        expanded_image.setImageResource(imageResId)

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBoundsInt)
        container.getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Extend start bounds horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.alpha = 0f
        expanded_image.visibility = View.VISIBLE

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        // commented to scale on itsel
        // expanded_image.pivotX = 0f
        // expanded_image.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
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
                    relax(thumbView, startBounds, startScale, imageResId, expanded_image, container)
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
        container: FrameLayout
    ) {
        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        currentAnimator?.cancel()
        countDownTimer?.cancel()

        // Animate the four positioning/sizing properties in parallel,
        // back to their original values.
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
                    thumbView.alpha = 1f
                    expanded_image.visibility = View.GONE
                    count++
                    if (doAgain) {
                        meta.value = "$count/${activeMode?.repetitions!!}"
                        progress.value = progress.value?.plus(1)
                        contract(thumbView, imageResId, expanded_image, container)
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

            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished < duration) {
                    counter.value = ((millisUntilFinished + 1000) / 1000).toString()
                }
            }

            override fun onFinish() {
                counter.value = (duration / 1000).toString()
            }

        }
    }

    fun getTrainingDiary(
        date: String
    ) {
        diary.value = BaseModel(BaseModel.Status.LOADING, null, null)
        serviceCalendarContract.getTrainingDiaryDay(date, {
            it?.let {
                diary.value = BaseModel(BaseModel.Status.SUCCESS, it, null)
            } ?: run {
                diary.value = BaseModel(BaseModel.Status.DEFAULT, null, null)
            }
        }, {
            diary.value = BaseModel(BaseModel.Status.ERROR, null, it)
        })

    }

}