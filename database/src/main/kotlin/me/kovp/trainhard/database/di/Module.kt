package me.kovp.trainhard.database.di

import androidx.room.Room
import me.kovp.trainhard.database.AppDatabase
import me.kovp.trainhard.database.MuscleGroupsProvider
import me.kovp.trainhard.database.completed_set.CompletedSetMapper
import me.kovp.trainhard.database.completed_set.CompletedSetsApiImpl
import me.kovp.trainhard.database.exercises.ExerciseMapper
import me.kovp.trainhard.database.exercises.ExercisesApiImpl
import me.kovp.trainhard.database.muscle_groups.MuscleGroupsApiImpl
import me.kovp.trainhard.database.muscle_groups.MuscleGroupsMapper
import me.kovp.trainhard.database_api.CompletedSetsApi
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
    single {
        val db: AppDatabase = get()

        CompletedSetMapper(
            exerciseDao = db.exercisesDao(),
            exerciseMapper = get(),
        )
    }

    //Providers' section
    single {
        val db: AppDatabase = get()
        MuscleGroupsProvider(muscleGroupDao = db.muscleGroupsDao())
    }

    // APIs' section
    single<MuscleGroupsApi> {
        val db: AppDatabase = get()

        MuscleGroupsApiImpl(
            muscleGroupsProvider = get(),
            muscleGroupDao = db.muscleGroupsDao(),
            muscleGroupMapper = get(),
        )
    }

    single<ExercisesApi> {
        val db: AppDatabase = get()

        ExercisesApiImpl(
            exerciseDao = db.exercisesDao(),
            muscleGroupsProvider = get(),
            exerciseMapper = get(),
        )
    }

    single<CompletedSetsApi> {
        val db: AppDatabase = get()

        CompletedSetsApiImpl(
            completedSetsDao = db.completedExercisesDao(),
            muscleGroupsProvider = get(),
            completedSetMapper = get(),
        )
    }
}
