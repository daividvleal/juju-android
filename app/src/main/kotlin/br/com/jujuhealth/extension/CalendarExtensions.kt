package br.com.jujuhealth.extension

import br.com.jujuhealth.data.request.DATE_FORMAT_KEY
import java.util.*

fun Calendar.getTodayFormattedKey(): String{
    return DATE_FORMAT_KEY.format(Calendar.getInstance().time).toString()
}