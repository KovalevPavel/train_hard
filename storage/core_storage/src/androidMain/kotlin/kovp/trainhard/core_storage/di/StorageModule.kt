package kovp.trainhard.core_storage.di

import android.content.Context
import kovp.trainhard.core_storage.DataStoreWrapperImpl
import kovp.trainhard.core_storage.datastore.createDataStore
import kovp.trainhard.core_storage.nosql.BookImpl
import kovp.trainhard.domain_storage.Book
import kovp.trainhard.domain_storage.GymCardQualifier
import kovp.trainhard.domain_storage.DataStoreWrapper
import org.koin.dsl.module

private const val CONFIGS_BOOK_NAME = "configs"

fun storageModule(context: Context) = module {
    single<DataStoreWrapper>(qualifier = GymCardQualifier) {
        DataStoreWrapperImpl(preferences = createDataStore(context))
    }

    single<Book> { BookImpl(bookName = CONFIGS_BOOK_NAME) }
}
