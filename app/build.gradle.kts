plugins {
    alias(libs.plugins.androidApplication)

}

android {
    namespace = "com.example.studentmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.studentmanager"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true  // <- mana shu qatorni qo'shing
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro -keep class com.github.mikephil.charting.** { *; }"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    annotationProcessor(libs.room.compiler)

    // Gson
    implementation(libs.gson)

    // ViewPager2 (duplicate material removed)
    implementation(libs.viewpager2)

    // Add these if you need them
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    implementation ("com.google.android.material:material:1.10.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

}