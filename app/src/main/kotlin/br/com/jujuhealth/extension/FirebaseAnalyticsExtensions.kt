package br.com.jujuhealth.extension

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

fun FirebaseAnalytics.log(id: String, name: String, content: String){
    val bundle = Bundle()
    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content)
    logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
}