package br.com.jujuhealth.data.request.calendar

import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.data.request.COLLECTION_DIARY
import br.com.jujuhealth.data.request.COLLECTION_TRAINING_DIARY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore

class ServiceCalendar(private val database: FirebaseFirestore, private val auth: FirebaseAuth) : ServiceCalendarContract {

    override fun getTrainingDiaryDay(
        date: String,
        success: (TrainingDiary?) -> Unit,
        error: (Exception?) -> Unit
    ) {
        auth.currentUser?.uid?.let { uid ->
            database
                .collection(COLLECTION_TRAINING_DIARY)
                .document(uid)
                .collection(COLLECTION_DIARY)
                .document(date)
                .get()
                .addOnSuccessListener {
                    success(it.toObject(TrainingDiary::class.java))
                }
                .addOnFailureListener {
                    error(it)
                }
        } ?: run {
            error(Exception())
        }
    }

    override fun getTrainingDiaryRange(
        startDate: String,
        endDate: String,
        success: (List<TrainingDiary>?) -> Unit,
        error: (Exception?) -> Unit
    ) {
        auth.currentUser?.uid?.let { uid ->
            database
                .collection(COLLECTION_TRAINING_DIARY)
                .document(uid)
                .collection(COLLECTION_DIARY)
                .whereGreaterThanOrEqualTo(FieldPath.documentId(), startDate)
                .whereLessThanOrEqualTo(FieldPath.documentId(), endDate)
                .get()
                .addOnSuccessListener {
                    success(it.toObjects(TrainingDiary::class.java))
                }
                .addOnFailureListener {
                    error(it)
                }
        } ?: run {
            error(Exception())
        }

    }

    override fun getTrainingAll(
        success: (List<TrainingDiary>?) -> Unit,
        error: (Exception?) -> Unit
    ) {
        auth.currentUser?.uid?.let { uid ->
            database
                .collection(COLLECTION_TRAINING_DIARY)
                .document(uid)
                .collection(COLLECTION_DIARY).get()
                .addOnSuccessListener {
                    success(it.toObjects(TrainingDiary::class.java))
                }
                .addOnFailureListener {
                    error(it)
                }
        } ?: run {
            error(Exception())
        }
    }

    override fun insertTrainingDiary(
        date: String,
        trainingDiary: TrainingDiary,
        success: () -> Unit,
        error: (Exception?) -> Unit
    ) {
        auth.currentUser?.uid?.let { uid ->
            database
                .collection(COLLECTION_TRAINING_DIARY)
                .document(uid)
                .collection(COLLECTION_DIARY)
                .document(date).set(trainingDiary)
                .addOnSuccessListener {
                    success()
                }
                .addOnFailureListener {
                    error(it)
                }
        } ?: run {
            error(Exception())
        }
    }

    override fun deleteTrainingDiary(
        date: String,
        trainingDiary: TrainingDiary,
        success: () -> Unit,
        error: (Exception?) -> Unit
    ) {
        auth.currentUser?.uid?.let { uid ->
            database.collection(COLLECTION_TRAINING_DIARY)
            .document(uid)
            .collection(COLLECTION_DIARY)
            .document(date).delete()
            .addOnSuccessListener {
                success()
            }
            .addOnFailureListener {
                error(it)
            }
        } ?: run {
            error(Exception())
        }
    }


}