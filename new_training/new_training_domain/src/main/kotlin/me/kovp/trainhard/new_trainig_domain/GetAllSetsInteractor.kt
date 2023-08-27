package me.kovp.trainhard.new_trainig_domain

import me.kovp.trainhard.database_api.CompletedSetsApi

class GetAllSetsInteractor(
    private val exercisesApi: CompletedSetsApi,
) {
    suspend operator fun invoke() = exercisesApi.getAllSets()
}
