package me.kovp.trainhard.new_trainig_domain

import me.kovp.trainhard.database_api.CompletedSetsApi
import me.kovp.trainhard.database_api.models.CompletedSet

class UpdateSetInteractor(
    private val completedSetsApi: CompletedSetsApi,
) {
    suspend operator fun invoke(editedSet: CompletedSet) {
        completedSetsApi.editSet(newSet = editedSet)
    }
}
