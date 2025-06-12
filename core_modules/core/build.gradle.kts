plugins {
    id("th.platform.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }

        androidUnitTest.dependencies {
            implementation(project.dependencies.platform(libs.junit.bom))
            implementation(libs.junit.jupiter)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.junit.launcher)
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
