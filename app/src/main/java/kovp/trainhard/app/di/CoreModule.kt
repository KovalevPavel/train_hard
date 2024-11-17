package kovp.trainhard.app.di

import kovp.trainhard.app.ResourceProviderImpl
import org.koin.dsl.module
import trainhard.kovp.core.ResourceProvider

val coreModule = module {
    single<ResourceProvider> {
        ResourceProviderImpl(context = get())
    }
}
