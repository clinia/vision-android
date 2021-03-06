import dependency.Library
import dependency.network.Coroutines
import dependency.network.CliniaClient
import dependency.script.AtomicFu
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin-multiplatform")
    id("maven-publish")
    id("com.jfrog.bintray")
    id("com.github.kukuhyoniatmoko.buildconfigkotlin") version "1.0.5"
}

buildConfigKotlin {
    sourceSet("metadata") {
        className("BuildConfiguration")
        buildConfig(name = "version", value = Library.version)
    }
}

group = Library.group
version = Library.version

kotlin {
    metadata {
        mavenPublication {
            artifactId = Library.artifactCoreCommon
        }
    }
    jvm {
        mavenPublication {
            artifactId = Library.artifactCoreJvm
        }
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            kotlin.srcDirs("build/generated/source")
            dependencies {
                api(kotlin("stdlib-common"))
                api(Coroutines("core-common"))
                api(CliniaClient("common"))
                implementation(AtomicFu("common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                api(kotlin("stdlib-jdk8"))
                api(Coroutines("core"))
                api(CliniaClient("jvm"))
                implementation(AtomicFu())
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    publish = true
    setPublications("metadata", "jvm")

    pkg.apply {
        desc = ""
        repo = "maven"
        userOrg = System.getenv("BINTRAY_ORG")
        name = Library.packageName
        websiteUrl = "https://www.clinia.ca/"
        issueTrackerUrl =  "https://github.com/clinia/vision-android/issues"
        setLicenses("Apache-2.0")
        setLabels("Kotlin", "Clinia")
        vcsUrl = "https://github.com/clinia/vision-android.git"
        version.apply {
            name = Library.version
            vcsTag = Library.version
        }
    }
}

tasks {
    withType<KotlinCompile> {
        dependsOn("generateMetadataBuildConfigKotlin")
    }
}

configurations.create("compileClasspath") //FIXME: Workaround for https://youtrack.jetbrains.com/issue/KT-27170