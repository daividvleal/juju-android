package br.com.jujuhealth.features.main.exercise.animator

import android.widget.ProgressBar
import android.view.animation.Animation
import android.view.animation.Transformation

class ProgressBarAnimation(
    private val progressBar: ProgressBar,
    private val from: Int,
    private val to: Int
) : Animation(){

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        super.applyTransformation(interpolatedTime, t)
        val value = (from.toFloat()/100 + (to.toFloat() - from.toFloat()/100) * interpolatedTime)*100
        progressBar.progress = value.toInt()
    }

}