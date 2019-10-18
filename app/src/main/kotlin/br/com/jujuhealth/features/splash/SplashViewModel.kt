package br.com.jujuhealth.features.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.User
import br.com.jujuhealth.data.request.sign.ServiceAuthContract
import org.koin.core.KoinComponent

class SplashViewModel(private val serviceAuthContract: ServiceAuthContract) : ViewModel(),
    KoinComponent {

    private val baseModel: MutableLiveData<BaseModel<User, Exception>> = MutableLiveData()

    fun getBaseModel() = baseModel

    fun isUserLogged() = serviceAuthContract.checkUserLogged({
        baseModel.value = BaseModel(
            status = BaseModel.Status.SUCCESS,
            data = User.buildUser(it),
            error = null
        )
    }, {
        baseModel.value = BaseModel(
            status = BaseModel.Status.DEFAULT,
            data = null,
            error = null
        )
    })

}