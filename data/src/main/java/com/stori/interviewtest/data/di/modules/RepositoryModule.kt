package com.stori.interviewtest.data.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.stori.interviewtest.data.repository.AuthRepository
import com.stori.interviewtest.data.repository.AuthRepositoryFirebase
import com.stori.interviewtest.data.repository.MovementsRepository
import com.stori.interviewtest.data.repository.MovementsRepositoryImpl
import com.stori.interviewtest.data.repository.ProfileRepositoryImpl
import com.stori.interviewtest.data.repository.StoriDataRepository
import com.stori.interviewtest.data.repository.StoriRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    @Provides
    fun provideAuthRepository(auth: FirebaseAuth) = AuthRepositoryFirebase(auth)
    @Provides
    fun provideMovementsRepository() = MovementsRepositoryImpl()
    @Provides
    fun provideProfileRepository() = ProfileRepositoryImpl()
    @Provides
    fun provideBetssonRepository(repository: StoriDataRepository): StoriRepository {
        return repository
    }


}
