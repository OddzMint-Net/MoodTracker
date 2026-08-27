package com.odwa.moodtracker.di

import com.odwa.moodtracker.data.support.DefaultSupportMessageProvider
import com.odwa.moodtracker.domain.SupportMessageProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SupportModule {

    @Binds
    abstract fun bindSupportMessageProvider(defaultSupportMessageProvider: DefaultSupportMessageProvider): SupportMessageProvider
}