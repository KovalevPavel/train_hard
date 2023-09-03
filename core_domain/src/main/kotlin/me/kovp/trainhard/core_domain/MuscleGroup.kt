package me.kovp.trainhard.core_domain

sealed interface MuscleGroup {
    enum class Legs(val id: String) : MuscleGroup {
        QUADRICEPS(id = "legs_quad"),
        BICEPS(id = "legs_bic"),
        CALVES(id = "legs_calves"),
        ;

        companion object {
            fun find(id: String) = values().firstOrNull { it.id.equals(id, ignoreCase = true) }
        }
    }

    enum class Chest(val id: String) : MuscleGroup {
        UPPER_CHEST(id = "chest_upper"),
        LOWER_CHEST(id = "chest_lower"),
        ;

        companion object {
            fun find(id: String) = values().firstOrNull { it.id.equals(id, ignoreCase = true) }
        }
    }

    enum class Back(val id: String) : MuscleGroup {
        LATS(id = "back_lats"),
        LOIN(id = "back_loin"),
        TRAPEZIUS(id = "back_trapezius"),
        ;

        companion object {
            fun find(id: String) = values().firstOrNull { it.id.equals(id, ignoreCase = true) }
        }
    }

    enum class Deltas(val id: String) : MuscleGroup {
        ANTERIOR(id = "deltas_ant"),
        MIDDLE(id = "deltas_mid"),
        POSTERIOR(id = "deltas_post"),
        ;

        companion object {
            fun find(id: String) = values().firstOrNull { it.id.equals(id, ignoreCase = true) }
        }
    }

    enum class Arms(val id: String) : MuscleGroup {
        BICEPS(id = "arms_bic"),
        TRICEPS(id = "arms_tric"),
        FOREARMS(id = "arms_fore"),
        ;

        companion object {
            fun find(id: String) = values().firstOrNull { it.id.equals(id, ignoreCase = true) }
        }
    }

    object Abs : MuscleGroup {
        override fun toString(): String = "ABS"
    }

    companion object {
        const val ABS_ID = "abs_"

        fun getGroupId(group: MuscleGroup) = when (group) {
            is Legs -> group.id
            is Chest -> group.id
            is Back -> group.id
            is Deltas -> group.id
            is Arms -> group.id
            is Abs -> ABS_ID
        }

        fun getAllGroups(): List<MuscleGroup> = mutableListOf<MuscleGroup>().apply {
            Legs.values().let(::addAll)
            Chest.values().let(::addAll)
            Back.values().let(::addAll)
            Deltas.values().let(::addAll)
            Arms.values().let(::addAll)
            Abs.let(::add)
        }

        fun getGroupById(groupId: String): MuscleGroup? =
            Legs.find(groupId)
                ?: Chest.find(groupId)
                ?: Back.find(groupId)
                ?: Deltas.find(groupId)
                ?: Arms.find(groupId)
                ?: Abs.takeIf { groupId.equals(ABS_ID, ignoreCase = true) }
    }
}
