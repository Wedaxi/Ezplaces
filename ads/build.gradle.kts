import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 33

    val apiProperties: Properties by rootProject.extra

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        manifestPlaceholders["adsApplicationId"] = apiProperties.getProperty("adsApplicationId")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            buildConfigField("String", "BANNER_UNIT_ID", apiProperties.getProperty("bannerUnitId"))
            buildConfigField("String", "REWARDED_UNIT_ID", apiProperties.getProperty("rewardedUnitId"))
        }
        debug {
            buildConfigField("String", "BANNER_UNIT_ID", "\"ca-app-pub-3940256099942544/6300978111\"")
            buildConfigField("String", "REWARDED_UNIT_ID", "\"ca-app-pub-3940256099942544/1033173712\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    namespace = "com.places.compose.ads"
}

dependencies {
    implementation("com.google.android.gms:play-services-ads:21.4.0")
}