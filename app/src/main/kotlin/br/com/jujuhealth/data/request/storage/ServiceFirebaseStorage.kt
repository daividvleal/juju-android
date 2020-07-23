package br.com.jujuhealth.data.request.storage

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import java.io.File

class ServiceFirebaseStorage(
    private val firebaseStorage: FirebaseStorage
) : ServiceFirebaseStorageContract {

    override fun uploadFile(
        uriFile: Uri,
        fileName: String,
        success: (uri: Uri) -> Unit,
        error: (Exception?) -> Unit
    ) {
        val ref = firebaseStorage.getReference(fileName)
        val task = ref.putFile(uriFile)
        this.generateUrlDownload(ref, task, success, error)
    }

    override fun downloadFile(
        fileDir: String,
        fileType: String,
        fileExt: String,
        success: (file: File) -> Unit,
        error: (Exception?) -> Unit
    ) {
        val ref = firebaseStorage.reference.child(fileDir)
        val saveFileInto = File.createTempFile(fileType, fileExt)
        ref.getFile(saveFileInto).addOnSuccessListener {
            success(saveFileInto)
        }.addOnFailureListener(error)
    }

    override fun deleteFile(
        fileDir: String,
        success: (task: Task<Void>) -> Unit,
        error: (java.lang.Exception?) -> Unit
    ) {
        val ref = firebaseStorage.reference.child(fileDir)
        ref.delete().addOnFailureListener(error).addOnCompleteListener(success)
    }

    private fun generateUrlDownload(
        reference: StorageReference,
        task: StorageTask<UploadTask.TaskSnapshot>,
        success: (uri: Uri) -> Unit,
        error: (Exception?) -> Unit
    ) {
        task.continueWithTask { taskExecuted ->
            if(taskExecuted.isSuccessful) {
                reference.downloadUrl
            } else {
                taskExecuted.exception?.let {
                    throw it
                }
            }
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.let(success) ?: run {
                    error(Throwable("Unknown Error"))
                }
            } else {
                error(Throwable("Unknown Error!"))
            }
        }.addOnFailureListener(error)
    }

}