package kovp.trainhard.core_domain

/**
 * Мышцы
 * @property upperChest верх грудных
 * @property midChest середина грудных
 * @property lowerChest низ грудных
 * @property quadriceps квадрицепс
 * @property hamstrings бицепс бедра
 * @property calves икры
 * @property trapezius трапеции
 * @property teres круглая мышца спины
 * @property lats широчайшая мышца спины
 * @property loin поясница
 * @property deltoidsAnt передний пучок дельт
 * @property deltoidsMid средний пучок дельт
 * @property deltoidsPost задний пучок дельт
 * @property armsBiceps бицепс
 * @property armsTriceps трицепс
 * @property armsForearms предплечия
 * @property abs пресс
 */
object Muscles {

    // Грудные
    val upperChest = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.CHEST
        override val muscleId: String = "upper"
    }
    val midChest = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.CHEST
        override val muscleId: String = "middle"
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
    // поясница
    val loin = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.BACK
        override val muscleId: String = "loin"
    }
    val trapezius = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.BACK
        override val muscleId: String = "trapezius"
    }
    val teres = object : Muscle() {
        override val muscleGroup: MuscleGroup = MuscleGroup.BACK
        override val muscleId: String = "teres"
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
        // грудные
        upperChest,
        midChest,
        lowerChest,
        // ноги
        quadriceps,
        hamstrings,
        calves,
        // спина
        lats,
        loin,
        teres,
        trapezius,
        // плечи
        deltoidsAnt,
        deltoidsMid,
        deltoidsPost,
        // руки
        armsBiceps,
        armsTriceps,
        armsForearms,
        // пресс
        abs,
    )

    fun getMusclesByGroup(group: MuscleGroup): List<Muscle> = allMuscles
        .filter { it.muscleGroup == group }

    fun getMuscleByFullId(fullId: String) = allMuscles.firstOrNull {
        it.id.equals(fullId, ignoreCase = true)
    }

    fun getMuscleGroup(id: String) = allMuscles.firstOrNull {
        it.id.equals(id, ignoreCase = true)
    }
        ?.muscleGroup
}
