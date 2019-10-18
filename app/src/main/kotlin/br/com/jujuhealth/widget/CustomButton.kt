package br.com.jujuhealth.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import br.com.jujuhealth.R
import kotlinx.android.synthetic.main.button_custom.view.*

class CustomButton(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    private lateinit var startText: String
    private lateinit var endText: String
    private var imageDrawableRight: Int = 0

    init {
        View.inflate(context, R.layout.button_custom, this)

        context.withStyledAttributes(
            attrs,
            R.styleable.CustomButton,
            0,
            0
        ){
            startText = getString(R.styleable.CustomButton_btn_start_text) ?: ""
            endText = getString(R.styleable.CustomButton_btn_end_text) ?: ""
            imageDrawableRight = getResourceId(R.styleable.CustomButton_btn_image, 0)

            btn_text_start.text = startText
            btn_text_end.text = endText
            if(imageDrawableRight != 0){
                btn_image.setImageResource(imageDrawableRight)
            }
        }
    }

    fun setOnClick(click : () -> Unit){
        this.setOnClickListener {
            click()
        }
    }

}