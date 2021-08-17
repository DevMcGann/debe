package com.gsoft.debe.di

import com.google.firebase.firestore.FirebaseFirestore
import com.gsoft.debe.data.repository.PacienteRepository
import com.gsoft.debe.data.repository.PacienteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePacienteRepo(
        firestore: FirebaseFirestore,
    ): PacienteRepository {
        return PacienteRepositoryImpl(
            firestore

        )
    }

    @Provides
    @Singleton
    fun provideFirestore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

}