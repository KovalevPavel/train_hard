package me.kovp.trainhard.database_api.models

operator fun CompletedSet.plus(reps: Reps) = this.copy(reps = this.reps + reps)
