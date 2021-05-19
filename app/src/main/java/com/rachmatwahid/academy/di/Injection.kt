package com.rachmatwahid.academy.di

import android.content.Context
import com.rachmatwahid.academy.data.source.AcademyRepository
import com.rachmatwahid.academy.data.source.remote.RemoteDataSource
import com.rachmatwahid.academy.utils.JsonHelper

object Injection {

    fun provideRepository(context: Context): AcademyRepository {
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        return AcademyRepository.getInstance(remoteDataSource)
    }
}