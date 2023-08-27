package me.kovp.trainhard.database.di

import androidx.room.Room
import me.kovp.trainhard.database.AppDatabase
import me.kovp.trainhard.database.api_impl.ExerciseMapper
import me.kovp.trainhard.database.api_impl.ExercisesApiImpl
import me.kovp.trainhard.database.api_impl.MuscleGroupsApiImpl
import me.kovp.trainhard.database.api_impl.MuscleGroupsMapper
import me.kovp.trainhard.database_api.ExercisesApi
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

    single { MuscleGroupsMapper() }
    single { ExerciseMapper(muscleGroupMapper = get()) }

    single<MuscleGroupsApi> {
        val db: AppDatabase = get()

        MuscleGroupsApiImpl(
            muscleGroupDao = db.muscleGroupsDao(),
            muscleGroupMapper = get(),
        )
    }

    single<ExercisesApi> {
        val db: AppDatabase = get()

        ExercisesApiImpl(
            exerciseDao = db.exercisesDao(),
            muscleGroupDao = db.muscleGroupsDao(),
            exerciseMapper = get(),
        )
    }
}
