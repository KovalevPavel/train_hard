package utils

object Versions {
    val versionName: String get() = "${App.MAJOR}.${App.MINOR}.${App.PATCH}"
    val versionCode: Int get() = App.BUILD

    internal object App {
        const val MAJOR = 1
        const val MINOR = 0
        const val PATCH = 2
        const val BUILD = 4
        const val PACKAGE_ID = "kovp.trainhard"
    }

    internal object Sdk {
        const val MIN_SDK = 26
        const val TARGET_SDK = 36
    }
}