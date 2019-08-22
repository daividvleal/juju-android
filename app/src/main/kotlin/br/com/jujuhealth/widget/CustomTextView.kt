package br.com.jujuhealth.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.annotation.IntegerRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import br.com.jujuhealth.R
import kotlinx.android.synthetic.main.text_view_custom.view.*

class CustomTextView(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    private lateinit var textInfo: String
    private lateinit var hintText: String
    private var drawable: Int = 0

    init {
        View.inflate(context, R.layout.text_view_custom, this)

        context.withStyledAttributes(
            attrs,
            R.styleable.CustomTextView,
            0,
            0
        ){
            textInfo = getString(R.styleable.CustomTextView_text_info) ?: ""
            hintText = getString(R.styleable.CustomTextView_hint_text) ?: ""
            drawable = getResourceId(R.styleable.CustomTextView_drawable_resource_right, 0)

            text_info.text = textInfo
            edit_text.hint = hintText
            if(drawable != 0){
                edit_text.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,drawable,0)
            }
        }
    }

}