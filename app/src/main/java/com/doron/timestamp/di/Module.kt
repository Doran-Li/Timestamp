package com.doron.timestamp.di

import com.doron.timestamp.GlobalEventBus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideGlobalEventBus(): GlobalEventBus = GlobalEventBus()
}