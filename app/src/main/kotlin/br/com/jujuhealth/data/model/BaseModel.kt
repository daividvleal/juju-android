package br.com.jujuhealth.data.model

import java.io.Serializable

data class BaseModel<T, E>(
    var status: Status,
    var data: T? = null,
    var error: E? = null
): Serializable {
    enum class Status {
        DEFAULT, LOADING, SUCCESS, ERROR
    }
}