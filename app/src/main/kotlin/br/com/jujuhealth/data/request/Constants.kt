package br.com.jujuhealth.data.request

import java.text.SimpleDateFormat
import java.util.*

const val COLLECTION_USER = "users"
const val COLLECTION_TRAINING_DIARY = "training-diary" //access to uid that has diary
const val COLLECTION_DIARY = "diary" //access to dates that the specified user have exercise or urine loss
val DATE_FORMAT_KEY = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
val DATE_FORMAT_BIRTHDAY = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

