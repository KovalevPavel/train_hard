buildscript {
    val major = 1
    val minor = 0
    val patch = 1
    val build = 3

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
