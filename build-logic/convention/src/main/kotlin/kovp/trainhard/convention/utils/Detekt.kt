package kovp.trainhard.convention.utils

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import java.util.Properties

internal fun Project.configureDetekt() {
    val props = Properties().apply {
        file("${rootDir}/build-logic/build.properties").inputStream().use(this::load)
    }
    val javaVersion = props["javaVersion"].toString()

    extensions.configure<DetektExtension> {
        source.setFrom(
            DetektExtension.DEFAULT_SRC_DIR_KOTLIN,
            DetektExtension.DEFAULT_TEST_SRC_DIR_KOTLIN,
            DetektExtension.DEFAULT_SRC_DIR_JAVA,
            DetektExtension.DEFAULT_TEST_SRC_DIR_JAVA,
        )

        allRules = false
        autoCorrect = true
        buildUponDefaultConfig = true
        config.from("${rootDir}/lint_rules/src/main/resources/config/detekt.yml")

        tasks.withType<Detekt>().configureEach {
            jvmTarget = javaVersion

            reports {
                xml.required.set(false)
                sarif.required.set(false)
                txt.required.set(true)
                html.required.set(false)
                md.required.set(false)
            }
            dependsOn(":lint_rules:assemble")
        }

        tasks.withType<DetektCreateBaselineTask>().configureEach {
            jvmTarget = javaVersion
        }
    }

    dependencies {
        add("detektPlugins", project(":lint_rules"))
    }
}
