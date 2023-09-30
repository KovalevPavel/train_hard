package me.kovp.trainhard.database_api.errors

data class EntityExistsException(val title: String = ""): Throwable()
