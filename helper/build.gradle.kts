import dependency.Library
import dependency.network.CliniaClient
import dependency.network.Coroutines
import dependency.network.Ktor
import dependency.test.AndroidTestExt
import dependency.test.AndroidTestRunner
import dependency.test.Robolectric
import dependency.test.SL4J
import dependency.ui.*

plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    id("kotlinx-serialization")
    id("maven-publish")
    id("com.jfrog.bintray")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(17)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions.unitTests.isIncludeAndroidResources = true

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.srcDirs("src/androidMain/kotlin")
            res.srcDirs("src/androidMain/res")
        }
        getByName("test") {
            java.srcDirs("src/androidTest/kotlin")
            res.srcDirs("src/androidTest/res")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

group = Library.group
version = Library.version

kotlin {
    metadata {
        mavenPublication {
            artifactId = Library.artifactHelperCommon
        }
    }
    jvm {
        mavenPublication {
            artifactId = Library.artifactHelperJvm
        }
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    android {
        mavenPublication {
            artifactId = Library.artifactHelperAndroid
        }
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core"))
                api(CliniaClient("common"))
                api(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(Ktor("client-mock"))
                implementation(CliniaClient("common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                api(kotlin("stdlib-jdk8"))
                api(CliniaClient("jvm"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation(SL4J("simple"))
                implementation(CliniaClient("jvm"))
                implementation(Ktor("client-mock-jvm"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(kotlin("stdlib-jdk8"))
                api(AppCompat())
                api(RecyclerView())
                api(MaterialDesign())
                api(AndroidCore("ktx"))
                api(SwipeRefreshLayout())
                api(Paging())
                api(Coroutines("android"))
                api(CliniaClient("jvm"))
                api(Ktor("client-android"))
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(project(":core"))
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation(CliniaClient("jvm"))
                implementation(Ktor("client-mock-jvm"))
                implementation(AppCompat())
                implementation(Paging())
                implementation(MaterialDesign())
                implementation(AndroidCore("ktx"))
                implementation(SwipeRefreshLayout())
                implementation(AndroidTestRunner())
                implementation(AndroidTestExt())
                implementation(Robolectric())
            }
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    publish = true
    setPublications("metadata", "jvm", "androidRelease")

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

configurations.create("compileClasspath") //FIXME: Workaround for https://youtrack.jetbrains.com/issue/KT-27170