package com.teils.main_menu.di

import android.content.Context
import com.teils.main_menu.data.sources.MemorySource
import com.teils.main_menu.data.sources.XpSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainMenuModule {
    @Provides
    @Singleton
    fun provideXpSource(@ApplicationContext context: Context) = XpSource(context)

    @Provides
    @Singleton
    fun provideMemorySource(@ApplicationContext context: Context) = MemorySource(context)
}