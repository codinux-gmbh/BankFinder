plugins {
    kotlin("jvm")
    `java-library`
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}


val luceneUtilsVersion: String by project
val javaUtilsVersion: String by project

val junitVersion: String by project
val assertJVersion: String by project
val mockitoVersion: String by project

dependencies {
    api(project(":BankFinder"))

    implementation("net.dankito.search:lucene-4-utils:$luceneUtilsVersion")

    implementation("net.dankito.utils:java-utils:$javaUtilsVersion")


    // TODO: enable to reference BankFinder tests again to run LuceneBankFinderTest
//    testImplementation project(path: ':BankFinder', configuration: 'testOutput')

    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
}



ext["customArtifactId"] = "lucene-bank-finder"

apply(from = "../gradle/scripts/publish-codinux-repo.gradle.kts")