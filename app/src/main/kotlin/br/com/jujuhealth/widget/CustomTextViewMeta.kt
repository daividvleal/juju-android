package br.com.jujuhealth.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import br.com.jujuhealth.R
import br.com.jujuhealth.activeMode
import kotlinx.android.synthetic.main.text_view_custom_meta.view.*

class CustomTextViewMeta(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.text_view_custom_meta, this)

        context.withStyledAttributes(
            attrs,
            R.styleable.CustomTextViewMeta,
            0,
            0
        ) {
            text_meta.text = "0/${activeMode?.repetitions!!}"
        }
    }

    fun setMeta(meta: String){
        text_meta.text = meta
    }
}