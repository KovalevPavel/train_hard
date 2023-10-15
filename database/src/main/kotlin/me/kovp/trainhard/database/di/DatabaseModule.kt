package me.kovp.trainhard.database.di

import androidx.room.Room
import me.kovp.trainhard.database.AppDatabase
import me.kovp.trainhard.database.calendar.CalendarApiImpl
import me.kovp.trainhard.database.completed_exercise.CompletedExerciseApiImpl
import me.kovp.trainhard.database.completed_exercise.CompletedExerciseMapper
import me.kovp.trainhard.database.exercises.ExerciseMapper
import me.kovp.trainhard.database.exercises.ExercisesApiImpl
import me.kovp.trainhard.database_api.CalendarApi
import me.kovp.trainhard.database_api.CompletedExerciseApi
import me.kovp.trainhard.database_api.ExercisesApi
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

    single { ExerciseMapper() }
    single {
        val db: AppDatabase = get()

        CompletedExerciseMapper(
            exerciseDao = db.exercisesDao(),
            exerciseMapper = get(),
        )
    }

    // APIs' section
    single<ExercisesApi> {
        val db: AppDatabase = get()

        ExercisesApiImpl(
            exerciseDao = db.exercisesDao(),
            exerciseMapper = get(),
        )
    }

    single<CompletedExerciseApi> {
        val db: AppDatabase = get()

        CompletedExerciseApiImpl(
            completedExercisesDao = db.completedExercisesDao(),
            completedExerciseMapper = get(),
        )
    }

    single<CalendarApi> {
        CalendarApiImpl(
            database = get(),
            exerciseMapper = get(),
        )
    }
}
