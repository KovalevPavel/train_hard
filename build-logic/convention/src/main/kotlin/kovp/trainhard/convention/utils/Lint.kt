package kovp.trainhard.convention.utils

import com.android.build.api.dsl.Lint
import org.gradle.api.Project
import java.io.File

internal fun Project.configureLint(lint: Lint) {
    lint.abortOnError = true
    lint.warningsAsErrors = false
    lint.checkGeneratedSources = false
    lint.htmlReport = true
    lint.xmlReport = false
    lint.textReport = false
    lint.htmlOutput = htmlReportOutputPath()
}

internal fun Project.htmlReportOutputPath(): File =
    file("${layout.buildDirectory.asFile.get()}/reports/lint-results-debug.html")
