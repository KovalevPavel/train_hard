package kovp.trainhard.convention.utils

import com.android.build.api.dsl.CommonExtension

private val ignoreRules = listOf(
    "Overdraw",
    "VectorPath",
    "VectorRaster",
    "SetTextI18n",
    "AlwaysShowAction",
    "ClickableViewAccessibility",
    "IconLocation",
    "LockedOrientationActivity",
    "DataExtractionRules",
    "UnusedResources",
)

fun CommonExtension<*, *, *, *, *>.configureAndroidLint() = lint {
    abortOnError = false
    warningsAsErrors = false
    htmlReport = false
    textReport = false
    xmlReport = true
    sarifReport = false
    disable += ignoreRules
    checkDependencies = true
    showAll = true
}
