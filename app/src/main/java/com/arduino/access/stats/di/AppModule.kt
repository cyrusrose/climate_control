package com.arduino.access.stats.di

import com.arduino.access.room.data.RoomRep
import com.arduino.access.stats.data.StatsRep
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideStatsRep() = StatsRep(Firebase.database.getReference(
        "/users/usptu/stats"
    ))
}