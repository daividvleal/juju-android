package br.com.jujuhealth.extension

fun String?.isEmail(): Boolean{
    return this?.let{
        android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    } ?: run {
        false
    }
}

fun String?.isPassword(): Boolean{
    return this?.let{
        this.length >= 6
    } ?: run {
        false
    }
}