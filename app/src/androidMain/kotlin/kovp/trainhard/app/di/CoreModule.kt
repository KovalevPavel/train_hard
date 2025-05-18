package kovp.trainhard.app.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module

val coreModule = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }
}
