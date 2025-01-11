buildscript {
    val major = 1
    val minor = 0
    val patch = 0
    val build = 2

    extra.apply {
        set(
            "applicationConfig",
            mapOf(
                "versionCode" to build,
                "versionName" to "$major.$minor.$patch",
            ),
        )
    }
}
