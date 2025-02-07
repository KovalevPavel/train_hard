package kovp.trainhard.core_domain

enum class MuscleGroup(val groupId: String) {
    LEGS("legs"),
    BACK("back"),
    CHEST("chest"),
    DELTOIDS("deltoids"),
    ARMS("arms"),
    ABS("abs"),
    ;

    companion object {
        fun findById(id: String?) = MuscleGroup.entries.firstOrNull {
            it.groupId.equals(other = id, ignoreCase = true)
        }
    }
}
