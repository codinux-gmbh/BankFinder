
buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    val kotlinVersion: String by extra

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        classpath("dev.icerock.moko:resources-generator:0.24.3")
    }
}


// TODO: fix publishing
//def commonScriptsFile = new File(new File(project.gradle.gradleUserHomeDir, "scripts"), "commonScripts.gradle")
//if (commonScriptsFile.exists()) {
//    apply from: commonScriptsFile
//}


allprojects {
    group = "net.codinux.banking"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        google()
    }
}



tasks.register("jarAll") {
    dependsOn(
            "BankFinder:jvmJar",
            "LuceneBankFinder:jar"
    )
}