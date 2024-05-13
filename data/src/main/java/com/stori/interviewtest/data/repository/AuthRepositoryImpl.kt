package com.stori.interviewtest.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.stori.interviewtest.commons.Either
import com.stori.interviewtest.commons.Either.Left
import com.stori.interviewtest.commons.Either.Right
import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.data.error.GenericUserErrorContainer
import com.stori.interviewtest.data.error.UserError.Type.EXCEPTION
import com.stori.interviewtest.data.error.UserErrorContainer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await

const val USER_INFO_KEY = "photos"
const val PHOTO_URL_KEY = "photoUrl"

@Singleton
class AuthRepositoryFirebase @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    override val currentUser get() = auth.currentUser
    private val firestore = FirebaseFirestore.getInstance()
    private val storageRef = FirebaseStorage.getInstance().reference.child(USER_INFO_KEY)

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String,
        password: String
    ) = try {
        auth.signInWithEmailAndPassword(email, password).await()
        Right(true)
    } catch (e: Exception) {
        Left(GenericUserErrorContainer(EXCEPTION, e.message))
    }

    override fun signOut() = auth.signOut()

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)

    override fun saveInfoUser(user: UserStori): Flow<Either<UserErrorContainer, Boolean>> {
        return callbackFlow {
            try {
                auth.createUserWithEmailAndPassword(user.email, user.password).await()
                val cUser = currentUser
                if (cUser == null) {
                    trySend(Left(GenericUserErrorContainer(EXCEPTION, "Unauthorized used.")))
                } else {
                    val db = firestore
                    user.password = ""
                    val collection = db.collection(USER_INFO_KEY).document(cUser.uid)
                    collection.set(user)
                        .addOnSuccessListener {
                            user.id = cUser.uid
                            if (user.photoUrl.startsWith("file://")) {
                                uploadFile(user)
                            } else {
                                Tasks.forResult(Unit)
                            }
                        }.addOnFailureListener {
                            trySend(Left(GenericUserErrorContainer(EXCEPTION, "Fail to save profile info")))
                        }
                        .addOnSuccessListener { trySend(Right(true)) }
                        .addOnFailureListener { e ->
                            trySend(
                                Left(
                                    GenericUserErrorContainer(
                                        EXCEPTION,
                                        e.message
                                    )
                                )
                            )
                        }

                }
            } catch (e: Exception) {
                trySend(Left(GenericUserErrorContainer(EXCEPTION, e.message)))
            }
            awaitClose()
        }
    }

    private fun uploadFile(user: UserStori): Task<Void> {
        return uploadPhoto(user).continueWithTask { urlTask ->
            File(user.photoUrl).delete()
            user.photoUrl = urlTask.result.toString()
            firestore.collection(USER_INFO_KEY)
                .document(user.id)
                .update(PHOTO_URL_KEY, user.photoUrl)
        }
    }

    private fun uploadPhoto(user: UserStori): Task<Uri> {
        compressPhoto(user.photoUrl)
        val storageRef = storageRef.child(user.id)
        return storageRef.putFile(Uri.parse(user.photoUrl))
            .continueWithTask { uploadTask ->
                uploadTask.result?.storage?.downloadUrl
            }
    }

    private fun compressPhoto(path: String) {
        val imgFile = File(path.substringAfter("file://"))
        val bos = ByteArrayOutputStream()
        val bmp = try {
            val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            val ei = ExifInterface(imgFile.absolutePath)
            when (
                ei.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
                )
            ) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
                else -> bitmap
            }
        } catch (e: Exception) {
            BitmapFactory.decodeFile(imgFile.absolutePath)
        }
        bmp?.compress(Bitmap.CompressFormat.JPEG, 70, bos)
        val fos = FileOutputStream(imgFile)
        fos.write(bos.toByteArray())
        fos.flush()
        fos.close()
    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }
}
