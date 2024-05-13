package com.stori.interviewtest.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stori.interviewtest.commons.Either
import com.stori.interviewtest.commons.Either.Left
import com.stori.interviewtest.commons.Either.Right
import com.stori.interviewtest.data.UserStori
import com.stori.interviewtest.data.error.GenericUserErrorContainer
import com.stori.interviewtest.data.error.UserError.Type.EXCEPTION
import com.stori.interviewtest.data.error.UserErrorContainer
import com.stori.interviewtest.data.models.Movement
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProfileRepositoryImpl : ProfileRepository {
    private val fbAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun loadProfile(): Flow<Either<UserErrorContainer, UserStori?>> {
        return callbackFlow {
            val subscription = firestore.collection(USER_INFO_KEY)
                .document(fbAuth.uid.toString())
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        close(e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null && snapshot.exists()) {
                        val book = snapshot.toObject(UserStori::class.java)
                        book?.let {
                            trySend(Right(it))
                        }
                    } else {
                        trySend(Right(null))
                    }
                }
            awaitClose { subscription.remove() }
        }
    }
}
