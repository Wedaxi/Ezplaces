import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.places.compose.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        val apiPropertiesFile = rootProject.file("api.properties")
        val apiProperties = Properties()
        apiProperties.load(FileInputStream(apiPropertiesFile))

        buildConfigField("String", "ENCRYPTION_KEY", apiProperties.getProperty("encryptionKey"))
        buildConfigField("String", "ENCRYPTED_API_KEY", apiProperties.getProperty("encryptedApiKey"))

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.activity.ktx)

    implementation(libs.places)
    implementation(libs.google.maps.utils)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(libs.themis)
}