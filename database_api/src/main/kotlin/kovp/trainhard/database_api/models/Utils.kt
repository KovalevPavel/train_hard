package kovp.trainhard.database_api.models

operator fun CompletedExercise.plus(sets: Sets) = this.copy(sets = this.sets + sets)
operator fun CompletedExercise.minus(sets: Sets) = this.copy(sets = this.sets - sets)
