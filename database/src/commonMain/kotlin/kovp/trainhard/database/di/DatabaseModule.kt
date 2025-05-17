package kovp.trainhard.database.di

import androidx.room.Room
import kovp.trainhard.database.AppDatabase
import kovp.trainhard.database.calendar.CalendarApiImpl
import kovp.trainhard.database.completed_exercise.CompletedExerciseApiImpl
import kovp.trainhard.database.completed_exercise.CompletedExerciseMapper
import kovp.trainhard.database.exercises.ExerciseMapper
import kovp.trainhard.database.exercises.ExercisesApiImpl
import kovp.trainhard.database_api.CalendarApi
import kovp.trainhard.database_api.CompletedExerciseApi
import kovp.trainhard.database_api.ExercisesApi
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

    single { ExerciseMapper(configHolder = get()) }
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
        val db: AppDatabase = get()

        CalendarApiImpl(
            calendarDao = db.calendarDao(),
            configHolder = get(),
        )
    }
}
