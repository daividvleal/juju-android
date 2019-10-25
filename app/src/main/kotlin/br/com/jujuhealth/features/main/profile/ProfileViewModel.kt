package br.com.jujuhealth.features.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.User
import br.com.jujuhealth.data.request.main.ServiceMainContract
import br.com.jujuhealth.data.request.sign.ServiceAuthContract
import org.koin.core.KoinComponent

class ProfileViewModel(
    private val serviceMainContract: ServiceMainContract,
    private val serviceAuthContract: ServiceAuthContract
) : ViewModel(),
    KoinComponent {

    val user = MutableLiveData<BaseModel<User, Exception>>()

    fun getUser() {
        user.value = BaseModel(
            status = BaseModel.Status.LOADING,
            data = null,
            error = null
        )
        serviceMainContract.getUser({
            user.value = BaseModel(
                status = BaseModel.Status.SUCCESS,
                data = it,
                error = null
            )
        }, {
            user.value = BaseModel(
                status = BaseModel.Status.ERROR,
                data = null,
                error = it
            )
        })
    }


    fun signOut() {
        serviceAuthContract.signOut()
    }

}