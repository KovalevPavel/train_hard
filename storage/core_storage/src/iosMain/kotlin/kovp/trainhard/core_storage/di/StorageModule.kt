package kovp.trainhard.core_storage.di

import kovp.trainhard.core_storage.DataStoreWrapperImpl
import kovp.trainhard.core_storage.datastore.createDataStore
import kovp.trainhard.domain_storage.DataStoreWrapper
import kovp.trainhard.domain_storage.GymCardQualifier
import org.koin.dsl.module

fun storageModule() = module {
    single<DataStoreWrapper>(qualifier = GymCardQualifier) {
        DataStoreWrapperImpl(preferences = createDataStore())
    }

//    single<Book> { BookImpl(bookName = CONFIGS_BOOK_NAME) }
}