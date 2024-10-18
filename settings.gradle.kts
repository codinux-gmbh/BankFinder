pluginManagement {
    val kotlinVersion: String by settings
    val quarkusVersion: String by settings

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        kotlin("multiplatform") version kotlinVersion
        kotlin("jvm") version kotlinVersion

        kotlin("plugin.allopen") version kotlinVersion
        kotlin("plugin.noarg") version kotlinVersion

        id("io.quarkus") version quarkusVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}


include("BankFinder")

include("LuceneBankFinder")

include("BankListCreator")

include("BankFinderRest")
