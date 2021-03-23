package br.com.jujuhealth.features.main.changepassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.request.sign.ServiceAuth
import br.com.jujuhealth.data.request.sign.ServiceAuthContract
import org.koin.core.KoinComponent

class ChangePasswordViewModel(private val serviceAuthContract: ServiceAuthContract): ViewModel(), KoinComponent {

    val successUpdated = MutableLiveData<BaseModel<Boolean, Exception>>()

    fun updatePassword(pwdActual: String, pwd: String){
        successUpdated.value = BaseModel(BaseModel.Status.LOADING, null, null)
        serviceAuthContract.updatePassword(pwdActual, pwd, {
            successUpdated.value = BaseModel(BaseModel.Status.SUCCESS, true, null)
        }, {
            successUpdated.value = BaseModel(BaseModel.Status.ERROR, false, it)
        })
    }

}