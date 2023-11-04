plugins {
    id("io.quarkus")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.allopen")
}

repositories {
    mavenCentral()
}

val quarkusVersion: String by project
val lokiLoggerVersion: String by project

configurations.all {
    resolutionStrategy {
        // Quarkus overrides OkHttp version 4.x of LokiLogger with incompatible OkHttp version 3.x
        force("com.squareup.okhttp3:okhttp:4.11.0")
        force("com.squareup.okio:okio:3.4.0")
    }
}

dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:$quarkusVersion"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-resteasy-jackson")

    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-smallrye-health")
    implementation("io.quarkus:quarkus-micrometer")
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    implementation("io.quarkus:quarkus-container-image-jib")

    implementation(project(":BankFinder"))

    implementation("net.codinux.log:quarkus-loki-logger:$lokiLoggerVersion")
    implementation("net.codinux.log.kubernetes:codinux-kubernetes-info-retriever:$lokiLoggerVersion")
    implementation("net.codinux.log:kmp-log:1.1.2")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.quarkus:quarkus-jacoco")
}

group = "net.dankito.banking"
version = "1.0.0-SNAPSHOT"

val javaVersion = JavaVersion.VERSION_11

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = javaVersion.toString()
    kotlinOptions.javaParameters = true
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("javax.inject.Inject")
    annotation("javax.inject.Singleton")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}
