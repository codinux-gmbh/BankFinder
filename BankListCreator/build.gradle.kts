plugins {
    kotlin("jvm")
    `java-library`
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}



val assertJVersion: String by project
val logbackVersion: String by project

dependencies {
    api(project(":BankFinder"))

    implementation("org.docx4j:docx4j-JAXB-ReferenceImpl:8.1.3")


    testImplementation("junit:junit:4.12")
    testImplementation("org.assertj:assertj-core:$assertJVersion")

    testImplementation("ch.qos.logback:logback-core:$logbackVersion")
    testImplementation("ch.qos.logback:logback-classic:$logbackVersion")
}