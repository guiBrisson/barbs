pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("plugin.serialization").version(extra["kotlin.version"] as String)
    }
}

rootProject.name = "barbs"
