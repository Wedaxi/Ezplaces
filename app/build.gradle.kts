plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
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
            versionName = "0.7.0"

            val adsEnabled: Boolean by rootProject.extra
            buildConfigField("boolean", "ENABLE_ADS", "$adsEnabled")
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
        kotlinCompilerExtensionVersion = "1.5.5"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.places.compose"
}

dependencies {

    val activityVersion: String by rootProject.extra
    val accompanistVersion: String by rootProject.extra
    val composeVersion: String by rootProject.extra
    val coreKtsVersion: String by rootProject.extra
    val gsonVersion: String by rootProject.extra
    val koinVersion: String by rootProject.extra
    val lifecycleVersion: String by rootProject.extra
    val wearComposeVersion: String by rootProject.extra

    val adsEnabled: Boolean by rootProject.extra
    if (adsEnabled) {
        // "mobileImplementation"(project(path = ":ads"))
    } else {
        "mobileImplementation"(project(path = ":ads-disabled"))
    }

    implementation(project(path = ":data"))

    implementation("androidx.core:core-ktx:$coreKtsVersion")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.activity:activity-compose:$activityVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    implementation("com.google.android.material:material:1.12.0")

    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-perf-ktx")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")

    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:3.4.1")

    "mobileImplementation"("com.google.accompanist:accompanist-pager:$accompanistVersion")
    "mobileImplementation"("com.google.accompanist:accompanist-pager-indicators:$accompanistVersion")
    "mobileImplementation"("com.google.accompanist:accompanist-navigation-animation:$accompanistVersion")
    "mobileImplementation"("com.google.accompanist:accompanist-swiperefresh:$accompanistVersion")
    "mobileImplementation"("com.google.accompanist:accompanist-permissions:$accompanistVersion")
    "mobileImplementation"("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")

    "mobileImplementation"("com.google.code.gson:gson:$gsonVersion")

    implementation("io.coil-kt:coil-compose:2.2.2")

    implementation("com.jakewharton.timber:timber:5.0.1")

    "wearImplementation"("androidx.wear.compose:compose-material:$wearComposeVersion")
    "wearImplementation"("androidx.wear.compose:compose-foundation:$wearComposeVersion")

    "wearImplementation"("com.google.android.gms:play-services-wearable:18.2.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
}