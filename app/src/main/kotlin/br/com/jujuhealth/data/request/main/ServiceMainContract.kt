package br.com.jujuhealth.data.request.main

import br.com.jujuhealth.data.model.User

interface ServiceMainContract {

    fun getUser(success: (User?) -> Unit, error: (Exception?) -> Unit)

}