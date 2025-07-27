import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.vanniktech.maven.publish)
}

group = "io.github.ajiekcx"
version = "0.2.0"

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
            api(project(":declarative-snackbar-core"))
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

apiValidation {
    @OptIn(kotlinx.validation.ExperimentalBCVApi::class)
    klib {
        enabled = true
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(group.toString(), "declarative-snackbar-compose", version.toString())

    pom {
        name = "declarative-snackbar-compose"
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
}
