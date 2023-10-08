package me.kovp.trainhard.core_storage.di

import android.content.Context
import me.kovp.core_storage.R
import me.kovp.trainhard.core_storage.SharedPreferencesWrapperImpl
import me.kovp.trainhard.domain_storage.GymCardQualifier
import me.kovp.trainhard.domain_storage.SharedPreferencesWrapper
import org.koin.dsl.module

fun storageModule(context: Context) = module {
    single<SharedPreferencesWrapper>(qualifier = GymCardQualifier) {
        SharedPreferencesWrapperImpl(
            preferences = context.getSharedPreferences(
                context.resources.getString(R.string.fitness_card_prefs),
                Context.MODE_PRIVATE,
            )
        )
    }
}
