package me.kovp.trainhard.database.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import me.kovp.trainhard.database.AppDatabase

@Component(
    modules = [DatabaseModule::class],
)
interface DatabaseComponent {
    val database: AppDatabase

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun bind(): DatabaseComponent

        companion object {
            operator fun invoke(): Builder = DaggerDatabaseComponent.builder()
        }
    }
}
