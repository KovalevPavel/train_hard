package me.kovp.trainhard.database.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.kovp.trainhard.database.AppDatabase

@Module
class DatabaseModule {
    @Provides
    fun initDatabase(applicationContext: Application): AppDatabase = Room.databaseBuilder(
        context = applicationContext,
        klass = AppDatabase::class.java,
        name = "app_database",
    )
        .build()
}
