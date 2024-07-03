plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.firebase.perf)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.android)
}

object Constants {
    const val ADS_ENABLED = false
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.places.compose"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions += "device"
    productFlavors {
        create("mobile") {
            dimension = "device"
            minSdk = 21
            versionCode = 13
            versionName = "0.7.1"

            buildConfigField("boolean", "ENABLE_ADS", "${Constants.ADS_ENABLED}")
        }
        create("wear") {
            dimension = "device"
            // applicationIdSuffix = ".wear"
            minSdk = 25
            versionCode = 1
            versionName = "0.1.0"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeKotlinCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.places.compose"
}

dependencies {

    if (Constants.ADS_ENABLED) {
        // "mobileImplementation"(project(path = ":ads"))
    } else {
        "mobileImplementation"(project(path = ":ads-disabled"))
    }

    implementation(project(path = ":data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.google.material)

    implementation(platform(libs.google.firebase.bom))
    implementation(libs.bundles.firebase)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.livedata)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    "mobileImplementation"(libs.bundles.accompanist)

    "mobileImplementation"(libs.gson)

    implementation(libs.coil.compose)

    implementation(libs.timber)

    "wearImplementation"(libs.wear.compose.material)
    "wearImplementation"(libs.wear.compose.foundation)

    "wearImplementation"(libs.wear.play.services)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.compose.ui.tooling)
}