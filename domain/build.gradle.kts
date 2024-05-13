plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.stori.interviewtest.domain"

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":commons"))
    implementation(project(":data"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.androidx.test.runner)
    testImplementation(libs.mockk)
}
