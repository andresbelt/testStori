import com.android.build.api.dsl.LibraryDefaultConfig
import java.util.Locale

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.services.version)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.stori.interviewtest.data"

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        buildConfigFieldFromGradleProperty("API_BASE_URL")
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
        animationsDisabled = true
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":commons"))

    implementation(libs.retrofit.module)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.compiler)

    implementation(libs.moshi)
    implementation(libs.moshi.converter)

    implementation(libs.logging.interceptor)
    implementation(libs.livedata)

    implementation(libs.hilt.android)
    implementation("androidx.exifinterface:exifinterface:1.3.7")
    kapt(libs.hilt.compiler)

    implementation(libs.junit)
    implementation(libs.lifecycle.testing)
    implementation(libs.coroutine.test)
    implementation(libs.mockito.kotlin)
    implementation(libs.mockito.inline)
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.androidx.test.rules)
    testImplementation(libs.mockk)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.firestore)

}

fun LibraryDefaultConfig.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue = project.properties[gradlePropertyName] as? String
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName = "GRADLE_$gradlePropertyName".uppercase(Locale.getDefault())
    buildConfigField("String", androidResourceName, propertyValue)
}
