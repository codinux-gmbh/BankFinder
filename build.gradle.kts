
buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    val kotlinVersion: String by extra

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}


allprojects {
    group = "net.codinux.banking"
    version = "0.5.0"

    ext["projectDescription"] = "Library to find German banks"
    ext["sourceCodeRepositoryBaseUrl"] = "github.com/codinux-gmbh/BankFinder"


    repositories {
        mavenCentral()
        google()
    }
}



tasks.register("publishAllToMavenLocal") {
    dependsOn(
        ":BankFinder:publishToMavenLocal",
        ":LuceneBankFinder:publishToMavenLocal"
    )
}

tasks.register("publishAll") {
    dependsOn(
        ":BankFinder:publish"
    )
}

tasks.register("jarAll") {
    dependsOn(
            "BankFinder:jvmJar",
            "LuceneBankFinder:jar"
    )
}