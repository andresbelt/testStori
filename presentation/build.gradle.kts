@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.stori.interviewtest.presentation"

    composeOptions {
        kotlinCompilerExtensionVersion =  libs.versions.kotlincompilerversion.get()
    }

    buildFeatures {
        compose = true
    }

    defaultConfig {
        applicationId = "com.stori.interviewtest"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {

        }

        getByName("release") {
            isMinifyEnabled = true
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
}


dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":commons"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.coil.kt.compose)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    kapt(libs.androidx.lifecycle.compiler)
    implementation(libs.google.android.material)
    implementation(libs.material.icons.extended)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.test.ext.junit)
    implementation(libs.androidx.test.rules)
    testImplementation(libs.junit)
    testImplementation(libs.lifecycle.testing)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.androidx.test.runner)
    testImplementation(libs.mockk)
}

kapt {
    correctErrorTypes = true
}

