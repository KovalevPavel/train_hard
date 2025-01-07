package kovp.trainhard.core_storage.di

import android.content.Context
import kovp.trainhard.core_storage.R
import kovp.trainhard.core_storage.SharedPreferencesWrapperImpl
import kovp.trainhard.core_storage.nosql.BookImpl
import kovp.trainhard.domain_storage.Book
import kovp.trainhard.domain_storage.GymCardQualifier
import kovp.trainhard.domain_storage.SharedPreferencesWrapper
import org.koin.dsl.module

private const val CONFIGS_BOOK_NAME = "configs"

fun storageModule(context: Context) = module {
    single<SharedPreferencesWrapper>(qualifier = GymCardQualifier) {
        SharedPreferencesWrapperImpl(
            preferences = context.getSharedPreferences(
                context.resources.getString(R.string.fitness_card_prefs),
                Context.MODE_PRIVATE,
            )
        )
    }

    single<Book> { BookImpl(bookName = CONFIGS_BOOK_NAME) }
}
