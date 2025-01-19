package kovp.trainhard.app.di

import kotlinx.serialization.json.Json
import kovp.trainhard.app.ResourceProviderImpl
import org.koin.dsl.module
import trainhard.kovp.core.ResourceProvider

val coreModule = module {
    single<ResourceProvider> {
        ResourceProviderImpl(context = get())
    }
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }
}
