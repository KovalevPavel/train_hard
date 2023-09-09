package me.kovp.trainhard.core_domain

object Muscles {

    // Грудные
    val upperChest = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.CHEST
        override val muscleId: String = "upper"
    }
    val lowerChest = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.CHEST
        override val muscleId: String = "lower"
    }

    // Ноги
    val quadriceps = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.LEGS
        override val muscleId: String = "quadriceps"
    }
    val hamstrings = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.LEGS
        override val muscleId: String = "hamstrings"
    }
    val calves = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.LEGS
        override val muscleId: String = "calves"
    }

    // Спина
    val lats = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.BACK
        override val muscleId: String = "lats"
    }
    val loin = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.BACK
        override val muscleId: String = "loin"
    }
    val trapezius = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.BACK
        override val muscleId: String = "trapezius"
    }

    // Плечи
    val deltoidsAnt = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.DELTOIDS
        override val muscleId: String = "anterior"
    }
    val deltoidsMid = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.DELTOIDS
        override val muscleId: String = "middle"
    }
    val deltoidsPost = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.DELTOIDS
        override val muscleId: String = "posterior"
    }

    // Руки
    val armsBiceps = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.ARMS
        override val muscleId: String = "biceps"
    }
    val armsTriceps = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.ARMS
        override val muscleId: String = "triceps"
    }
    val armsForearms = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.ARMS
        override val muscleId: String = "forearms"
    }

    // Пресс
    val abs = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.ABS
        override val muscleId: String = "abs"
    }

    val allMuscles = listOf(
        upperChest,
        lowerChest,
        quadriceps,
        hamstrings,
        calves,
        lats,
        loin,
        trapezius,
        deltoidsAnt,
        deltoidsMid,
        deltoidsPost,
        armsBiceps,
        armsTriceps,
        armsForearms,
        abs,
    )

    inline fun <reified T : MuscleGroup> getMusclesByGroup(): List<Muscle> = allMuscles
        .filter { it.muscleGroup is T }
}
