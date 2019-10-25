package br.com.jujuhealth.extension

import android.content.Context
import br.com.jujuhealth.R
import br.com.jujuhealth.data.request.DATE_FORMAT_KEY
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.getFormattedKey(): String{
    return DATE_FORMAT_KEY.format(this.time).toString()
}

fun Calendar.toDateDetailDialogFormat(context: Context): String{
    var format = "dd '" +
            context.getString(R.string.of) +
            "' '" +
            when(Calendar.getInstance().get(Calendar.MONTH)){
                0 -> {
                    context.getString(R.string.january) + "' '" + context.getString(R.string.of) + "' "
                }
                1 -> {
                    context.getString(R.string.february) + "' '" + context.getString(R.string.of) + "' "
                }
                2 -> {
                    context.getString(R.string.march) + "' '" + context.getString(R.string.of) + "' "
                }
                3 -> {
                    context.getString(R.string.april) + "' '" + context.getString(R.string.of) + "' "
                }
                4 -> {
                    context.getString(R.string.may) + "' '" + context.getString(R.string.of) + "' "
                }
                5 -> {
                    context.getString(R.string.jun) + "' '" + context.getString(R.string.of) + "' "
                }
                6 -> {
                    context.getString(R.string.july) + "' '" + context.getString(R.string.of) + "' "
                }
                7 -> {
                    context.getString(R.string.august) + "' '" + context.getString(R.string.of) + "' "
                }
                8 -> {
                    context.getString(R.string.september) + "' '" + context.getString(R.string.of) + "' "
                }
                9 -> {
                    context.getString(R.string.october) + "' '" + context.getString(R.string.of) + "' "
                }
                10 -> {
                    context.getString(R.string.november) + "' '" + context.getString(R.string.of) + "' "
                }
                11 -> {
                    context.getString(R.string.december) + "' '" + context.getString(R.string.of) + "' "
                }
                else -> {}
            } +
            "yyyy"
    return SimpleDateFormat(format, Locale.ENGLISH).format(this.time)
}