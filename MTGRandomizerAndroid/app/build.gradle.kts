plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.pixeledberry.mtgrandomizer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pixeledberry.mtgrandomizer"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.1.0"
    }

    // One fixed signing key for every build, so a new version installs over an older one and keeps its decks.
    signingConfigs {
        create("mtgrandomizer") {
            storeFile = file("mtgrandomizer.jks")
            storeType = "pkcs12"
            storePassword = "mtgrandomizer"
            keyAlias = "mtgrandomizer"
            keyPassword = "mtgrandomizer"
        }
    }
    buildTypes {
        getByName("debug") { signingConfig = signingConfigs.getByName("mtgrandomizer") }
        getByName("release") { signingConfig = signingConfigs.getByName("mtgrandomizer") }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    implementation("androidx.webkit:webkit:1.11.0")
    implementation("androidx.core:core:1.13.1")
}
