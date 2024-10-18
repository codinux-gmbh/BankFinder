plugins {
    kotlin("multiplatform")
    id("maven-publish")

//    id("dev.icerock.mobile.multiplatform-resources")
}


ext["artifactName"] = "bank-finder"


kotlin {
    jvmToolchain(8)

    compilerOptions {
        // suppresses compiler warning: [EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING] 'expect'/'actual' classes (including interfaces, objects, annotations, enums, and 'actual' typealiases) are in Beta.
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }


    jvm {
        withJava()

        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js {
        binaries.executable()

        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    useFirefoxHeadless()
                }
            }
        }

        nodejs()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    watchosArm64()
    watchosSimulatorArm64()
    tvosArm64()
    tvosSimulatorArm64()

    applyDefaultHierarchyTemplate()


    val javaUtilsVersion: String by project
    val slf4jVersion: String by project

    sourceSets {
        commonMain {
            dependencies {
//                implementation("dev.icerock.moko:resources:0.24.3")
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }


        jvmMain {
            dependencies {
                api("net.dankito.utils:java-utils:$javaUtilsVersion")
            }
        }

        jvmTest {
            dependencies {
                implementation("org.slf4j:slf4j-simple:$slf4jVersion")
            }

        }
    }
}


//multiplatformResources {
//    resourcesPackage.set("net.dankito.banking.bankfinder")
//    resourcesClassName.set("BankFinderRes")
//    resourcesVisibility.set(dev.icerock.gradle.MRVisibility.Internal)
////    iosBaseLocalizationRegion.set("en") // optional, default "en"
////    iosMinimalDeploymentTarget.set("11.0") // optional, default "9.0"
//}