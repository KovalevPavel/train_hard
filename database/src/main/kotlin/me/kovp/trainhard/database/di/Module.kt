package me.kovp.trainhard.database.di

import androidx.room.Room
import me.kovp.trainhard.database.AppDatabase
import me.kovp.trainhard.database.api_impl.MuscleGroupsApiImpl
import me.kovp.trainhard.database_api.MuscleGroupsApi
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = "app_database",
        )
            .build()
    }

    single<MuscleGroupsApi> {
        val db: AppDatabase = get()

        MuscleGroupsApiImpl(
            muscleGroupDao = db.muscleGroupsDao(),
        )
    }
}
