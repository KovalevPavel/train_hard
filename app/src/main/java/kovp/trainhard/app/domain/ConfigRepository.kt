package kovp.trainhard.app.domain

interface ConfigRepository {
    fun getExercisesConfig(): ExercisesConfig
}
