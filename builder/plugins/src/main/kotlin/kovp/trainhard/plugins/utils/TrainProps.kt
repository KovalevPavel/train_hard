package kovp.trainhard.plugins.utils

import org.gradle.api.Project
import java.util.Properties

object TrainProps {
    private val properties: Properties = Properties()
    private var isInitialized = false

    context(Project)
    fun getProperties(): Properties = when {
        isInitialized -> {
            properties
        }

        else -> {
            properties.let {
                file("../builder/plugins/build.properties").inputStream().use(it::load)
            }
            isInitialized = true
            properties
        }
    }
}
