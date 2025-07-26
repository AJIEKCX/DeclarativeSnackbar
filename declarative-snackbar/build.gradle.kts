import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "io.github.ajiekcx"
version = "0.1.0"

kotlin {
    explicitApi()

    androidTarget {
        publishLibraryVariants("release")

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
        }
    }
}

android {
    namespace = "io.github.ajiekcx.declarativeSnackbar"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}


mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(group.toString(), "declarative-snackbar", version.toString())

    pom {
        name = "declarative-snackbar"
        description = "A declarative API for Snackbars in Compose Multiplatform that makes it easy to display and manage snackbar messages in your application."
        inceptionYear = "2025"
        url = "https://github.com/AJIEKCX/DeclarativeSnackbar/"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "@AJIEKCX"
                name = "Alexey Panov"
                url = "https://github.com/AJIEKCX/"
            }
        }
        scm {
            url = "https://github.com/AJIEKCX/DeclarativeSnackbar/"
            connection = "scm:git:git://github.com/AJIEKCX/DeclarativeSnackbar.git"
            developerConnection = "scm:git:ssh://git@github.com/AJIEKCX/DeclarativeSnackbar.git"
        }
    }

    pom {
        name.set(project.properties["POM_NAME"].toString())
        description.set(project.description)
        url.set("https://github.com/AJIEKCX/DeclarativeSnackbar")
        issueManagement {
            url.set("https://github.com/AJIEKCX/DeclarativeSnackbar/issues")
        }

        scm {
            url.set("https://github.com/AJIEKCX/DeclarativeSnackbar")
            connection.set("scm:git://github.com/AJIEKCX/DeclarativeSnackbar.git")
            developerConnection.set("scm:git://github.com/AJIEKCX/DeclarativeSnackbar.git")
        }

        licenses {
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }

        developers {
            developer {
                id.set("AJIEKCX")
                name.set("Alexey Panov")
                email.set("panovalexsey@gmail.com")
            }
        }
    }
}
