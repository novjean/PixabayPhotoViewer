plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.novatech.pixabayphotoviewer"
    compileSdk = 35

//    packaging {
//        resources {
//            excludes.addAll(
//                listOf(
//                    "META-INF/LICENSE.md",
//                    "META-INF/LICENSE-notice.md",
//                    "META-INF/DEPENDENCIES",
//                    "META-INF/NOTICE",
//                    "META-INF/NOTICE.txt",
//                    "META-INF/LICENSE",
//                    "META-INF/LICENSE.txt"
//                )
//            )
//        }
//    }

    defaultConfig {
        applicationId = "com.novatech.pixabayphotoviewer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "API_KEY", "\"${project.properties["API_KEY"]}\"")

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

    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    testImplementation("junit:junit:4.12")
    kapt("com.github.bumptech.glide:compiler:4.11.0")

    // Paging
    implementation("androidx.paging:paging-runtime:3.2.0")

    // Activity KTX for viewModels()
    implementation("androidx.activity:activity-ktx:1.1.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-android-compiler:2.52")
    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Encrypted Shared Preferences
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // solves pending intent requires flag issue
    implementation ("androidx.work:work-runtime:2.7.1")

    // Testing
    testImplementation(libs.junit)
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    implementation("org.mockito:mockito-android:5.14.2")

    // MockK for mocking
    testImplementation("io.mockk:mockk:1.13.5")

    // Google Truth for assertions
    testImplementation("com.google.truth:truth:1.1.5")
    androidTestImplementation("com.google.truth:truth:1.1.5")

    // Test helpers for LiveData
    androidTestImplementation("android.arch.core:core-testing:1.1.1")

    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1") // For RecyclerView actions
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")

    // MockWebServer for mocking network responses
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
}