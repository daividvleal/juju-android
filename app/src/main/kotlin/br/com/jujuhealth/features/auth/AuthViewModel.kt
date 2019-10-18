package br.com.jujuhealth.features.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.User
import br.com.jujuhealth.data.request.sign.ServiceAuthContract
import org.koin.core.KoinComponent

class AuthViewModel(private val serviceAuthContract: ServiceAuthContract) : ViewModel(),
    KoinComponent {

    private val baseModel: MutableLiveData<BaseModel<User, Exception>> = MutableLiveData()

    fun getBaseModel() = baseModel

    fun signUp(name: String, birthday: String, email: String, password: String) {
        baseModel.value = BaseModel(BaseModel.Status.LOADING)
        serviceAuthContract.signUp(name, birthday, email, password, {
            baseModel.value = BaseModel(
                status = BaseModel.Status.SUCCESS,
                data = User.buildUser(it),
                error = null
            )
        }, {
            baseModel.value = BaseModel(
                status = BaseModel.Status.ERROR,
                data = null,
                error = it
            )
        })
    }

    fun signIn(email: String, password: String) {
        baseModel.value = BaseModel(BaseModel.Status.LOADING)
        serviceAuthContract.signIn(email, password, {
            baseModel.value = BaseModel(
                status = BaseModel.Status.SUCCESS,
                data = User.buildUser(it),
                error = null
            )
        }, {
            baseModel.value = BaseModel(
                status = BaseModel.Status.ERROR,
                data = null,
                error = it
            )
        })
    }

}