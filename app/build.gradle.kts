plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.wyrm.engine"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.wyrm.engine"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    ndk {
      abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a"))
    }

    externalNativeBuild {
      cmake {
        arguments.add("-DANDROID_WEAK_API_DEFS=ON")
      }
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    viewBinding = true
    buildConfig = true
  }
  externalNativeBuild {
    cmake {
      path = file("src/main/cpp/CMakeLists.txt")
      version = "3.22.1"
      buildStagingDirectory = file("build-native")
    }
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  implementation(libs.utilcodex)
  implementation(libs.jbullet)
  implementation(libs.dexter)
  implementation(libs.gson)
  implementation(libs.commons.text)
  implementation(libs.zip4j)
  implementation(libs.eventbus)
  implementation(libs.glm)
  implementation(libs.android.tiffbitmapfactory)
  implementation(libs.obj)
  implementation(libs.colorpicker)

  implementation(project(":filetree"))

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}