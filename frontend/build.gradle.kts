import org.jetbrains.kotlin.gradle.targets.js.nodejs.*

println("Gradle Version: " + GradleVersion.current().toString())
println("Java Version: " + JavaVersion.current().toString())

val ciProfile = if (ext.has("ci")) (ext.get("ci") as String).toBoolean() else false
val kotlinVersion = "1.7.20"

group = "com.linked-planet.plugin.jira"
version = "0.1.0-SNAPSHOT"

plugins {
    kotlin("js") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
}

dependencies {
    implementation(kotlin("stdlib-js", kotlinVersion))
    implementation("com.linked-planet.ui", "ui-kit-lib", "0.10.0")

    implementation(npm("@fortawesome/fontawesome-free", "^5.15.1"))
}

kotlin {
    sourceSets.all {
        languageSettings.optIn("kotlin.RequiresOptIn")
    }
    js {
        useCommonJs()
        browser {
            runTask {
                devServer = devServer?.copy(
                    // frontend is embedded, so no point in opening a separate browser window
                    open = false
                )
            }
            webpackTask {
                outputFileName = "${project.name}.js"
            }
            @OptIn(org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDceDsl::class)
            dceTask {
                // list exported, unused functions to protect from dead-code-elimination
                keep(
                    "frontend.startJiraConfig",
                    "frontend.stopJiraConfig"
                )
            }
        }
        binaries.executable()
    }
}



// without this, node will fail to execute in the Bitbucket Pipeline Build Container
if (ciProfile) {
    rootProject.plugins.withType(NodeJsRootPlugin::class.java) {
        rootProject.the<NodeJsRootExtension>().download = false
    }
}

repositories {
    mavenCentral()
}
