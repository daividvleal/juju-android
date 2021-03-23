package br.com.jujuhealth.data.request.storage

import android.net.Uri
import com.google.android.gms.tasks.Task
import java.io.File
import java.lang.Exception

interface ServiceFirebaseStorageContract {

    fun uploadFile(
        uriFile: Uri,
        fileName: String,
        success: (uri: Uri) -> Unit,
        error: (Exception?) -> Unit
    )

    fun downloadFile(
        fileDir: String,
        fileType: String,
        fileExt: String,
        success: (file: File) -> Unit,
        error: (Exception?) -> Unit
    )

    fun deleteFile(
        fileDir: String,
        success: (task: Task<Void>) -> Unit,
        error: (Exception?) -> Unit
    )

}