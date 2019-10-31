package br.com.jujuhealth.extension

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView

fun TextView.setTextAndMakePartClickble(
    text: String,
    startIndex: Int,
    endIndex: Int,
    click: () -> Unit
) {
    val spannableString = SpannableString(text)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(textView: View) {
            click()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }
    spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(StyleSpan(Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(ForegroundColorSpan(Color.WHITE), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = spannableString
    this.movementMethod = LinkMovementMethod.getInstance()
    this.highlightColor = Color.TRANSPARENT
}

fun TextView.setTextAndMarkNumberAndLevel(
    text: String
) {
    val strings = text.split(" ")
    val startNumber = 0
    val endNumber = strings[0].length
    val startLevel = endNumber + 8
    val endLevel = text.length
    val spannableString = SpannableString(text)
    spannableString.setSpan(StyleSpan(Typeface.BOLD), startNumber, endNumber, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(StyleSpan(Typeface.BOLD),  startLevel, endLevel, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = spannableString
}

fun TextView.setMarkLevel(
    text: String
) {
    val startLevel = 15
    val endLevel = text.length
    val spannableString = SpannableString(text)
    spannableString.setSpan(StyleSpan(Typeface.BOLD),  startLevel, endLevel, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = spannableString
}