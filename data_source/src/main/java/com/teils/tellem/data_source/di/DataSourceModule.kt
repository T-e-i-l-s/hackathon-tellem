package com.teils.tellem.data_source.di

import android.content.Context
import com.teils.tellem.data_source.data.repositories.CoursesRepository
import com.teils.tellem.data_source.data.repositories.PlacesRepository
import com.teils.tellem.data_source.data.source.lessons.CoursesSource
import com.teils.tellem.data_source.data.source.places.PlacesSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideLessonsSource(@ApplicationContext context: Context): CoursesSource =
        CoursesSource(context)

    @Provides
    @Singleton
    fun provideLessonsRepository(coursesSource: CoursesSource): CoursesRepository =
        CoursesRepository(coursesSource)

    @Provides
    @Singleton
    fun providePlacesSource(@ApplicationContext context: Context): PlacesSource =
        PlacesSource(context)

    @Provides
    @Singleton
    fun providePlacesRepository(placesSource: PlacesSource): PlacesRepository =
        PlacesRepository(placesSource)
}