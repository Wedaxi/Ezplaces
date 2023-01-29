plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.places.compose.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        val apiProperties: java.util.Properties by rootProject.extra

        buildConfigField("String", "ENCRYPTION_KEY", apiProperties.getProperty("encryptionKey"))
        buildConfigField("String", "ENCRYPTED_API_KEY", apiProperties.getProperty("encryptedApiKey"))

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    val activityVersion: String by rootProject.extra
    val koinVersion: String by rootProject.extra
    val roomVersion: String by rootProject.extra

    implementation("androidx.activity:activity-ktx:$activityVersion")

    implementation("com.google.android.libraries.places:places:3.0.0")
    implementation("com.google.maps.android:android-maps-utils:3.1.0")

    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")

    implementation("com.cossacklabs.com:themis:0.13.1")
}