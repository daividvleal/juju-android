package br.com.jujuhealth.widget

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import br.com.jujuhealth.R
import br.com.jujuhealth.extension.isName
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
            edit_text.inputType = InputType.TYPE_CLASS_TEXT
            if(drawable != 0){
                edit_text.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,drawable,0)
                setRightIconClickListener()
            }
        }
    }

    fun setClick(onClickListener: () -> Unit){
        edit_text.inputType = InputType.TYPE_NULL
        edit_text.setOnClickListener {
            onClickListener()
            hideKeyboard(it)
        }
        edit_text.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                onClickListener()
                hideKeyboard(view)
            }
        }
    }

    private fun hideKeyboard(view: View){
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getText(): String {
        return edit_text.text.toString()
    }

    fun setText(text: String) {
        edit_text.setText(text)
    }

    private fun setRightIconClickListener(){
        var result = false
        edit_text.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        edit_text.setOnTouchListener { _, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_UP) {
                if(motionEvent.rawX >= (edit_text.right - edit_text.compoundDrawables[2].bounds.width())) {
                    if(edit_text.inputType == InputType.TYPE_CLASS_TEXT){
                        edit_text.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    }else{
                        edit_text.inputType = InputType.TYPE_CLASS_TEXT
                    }
                    result = false
                }else{
                    requestFocus()
                }
            }
            result
        }
    }

    fun setError(type: TYPETEXT){
        val message = when(type){
            TYPETEXT.EMAIL -> { context.getString(R.string.invalid_email) }
            TYPETEXT.PASSWORD -> { context.getString(R.string.invalid_password) }
            TYPETEXT.OBEY -> { context.getString(R.string.obey) }
            TYPETEXT.INVALID -> { context.getString(R.string.invalid_field) }
        }
        edit_text.requestFocus()
        edit_text.error = message
    }

    companion object{
        enum class TYPETEXT{
            EMAIL,
            PASSWORD,
            OBEY,
            INVALID
        }
    }

}