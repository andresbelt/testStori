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
import com.stori.interviewtest.data.models.MovementsList
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val MOVEMENTS_KEY = "movements"

class MovementsRepositoryImpl : MovementsRepository {
    private val fbAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun loadMovements(): Flow<Either<UserErrorContainer,List<Movement?>?>> {
        return callbackFlow {
            val currentUser = fbAuth.currentUser
            val subscription = firestore.collection(MOVEMENTS_KEY)
                .document(fbAuth.uid.toString())
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        trySend(Left(GenericUserErrorContainer(EXCEPTION, e.message)))
                        close(e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null && snapshot.exists()) {
                        val book = snapshot.toObject(MovementsList::class.java)
                        book?.let {
                            trySend(Right(it.transactions))
                        }
                    } else {
                        trySend(Right(emptyList()))
                    }
                }

            awaitClose {
                subscription.remove()
            }
        }
    }

    companion object {
        const val USER_ID_KEY = "userId"
    }
}
