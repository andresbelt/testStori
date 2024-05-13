package com.stori.interviewtest

import android.content.Context
import com.stori.interviewtest.ui.FilePicker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HelperModule {
    @Provides
    fun providesFilePicker(
        @ApplicationContext context: Context
    ): FilePicker {
        return FilePicker(context, "com.stori.interviewtest.fileprovider")
    }
}
