plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id ("realm-android")
}

android {
    namespace = "com.rektstudios.trueweather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rektstudios.trueweather"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


        buildConfigField( "String", "API_KEY", "${properties.getValue("API_KEY")}")
        buildConfigField( "String", "TOKEN_KEY", "${properties.getValue("TOKEN_KEY")}")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.runner)
    implementation(libs.androidx.espresso.contrib)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.play.services.location)
    testImplementation (libs.core.testing)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation (libs.truth)
    testImplementation (libs.mockito.kotlin)
    testImplementation (libs.kotlinx.coroutines.test)
    androidTestImplementation (libs.core.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation (libs.truth)
    androidTestImplementation (libs.mockito.kotlin)
    androidTestImplementation (libs.kotlinx.coroutines.test)
    implementation(libs.mockito.android)
    androidTestImplementation (libs.mockito.android)


    // Coroutine
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.lifecycle.runtime)

    // LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Dagger - Hilt
    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler)
    ksp (libs.androidx.hilt.compiler)

    // For instrumentation tests
    androidTestImplementation  (libs.hilt.android.testing)
    kspAndroidTest (libs.hilt.compiler)

    // For local unit tests
    testImplementation (libs.hilt.android.testing)
    kspTest (libs.hilt.compiler)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Testing Navigation
    androidTestImplementation(libs.androidx.navigation.testing)


    // Glide
    implementation (libs.glide)
    annotationProcessor (libs.glide.compiler)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.glide.compiler)


    // Activity KTX for viewModels()
    implementation(libs.androidx.activity.ktx)

    debugImplementation(libs.androidx.fragment.testing)

    androidTestImplementation(libs.androidx.fragment)

    androidTestImplementation(libs.androidx.fragment.testing)
    implementation(libs.androidx.datastore.preferences.android)

    // Splash
    implementation(libs.androidx.core.splashscreen)

    // Swipe to Refresh
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

}
