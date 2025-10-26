# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


###############################################
# ⚙️ General Project Rules
###############################################

# Keep class and line numbers for crash logs (optional for debugging)
-keepattributes SourceFile,LineNumberTable

# Remove debug logs and assertions
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

###############################################
# 🏗 Room Database Rules
###############################################

# Keep Room database, entities, and DAO interfaces
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Database class * { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keep interface * implements androidx.room.RoomDatabase$Callback { *; }

# Keep generated schema info
-keepclassmembers class * {
    @androidx.room.* <fields>;
    @androidx.room.* <methods>;
}

# Keep models and entities in your package
-keep class com.d12.expirymonitor.model.** { *; }
-keep class com.d12.expirymonitor.data.localData.** { *; }

###############################################
# 🧩 Kotlin & Coroutines
###############################################

# Keep Kotlin metadata (used by Room + reflection)
-keep class kotlin.Metadata { *; }

# Keep coroutine-related classes
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

###############################################
# 🎨 Jetpack Compose Rules
###############################################

# Keep Compose runtime classes
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Keep Koin (if used)
-keep class org.koin.** { *; }
-dontwarn org.koin.**

###############################################
# 📦 Serialization / JSON (if any in future)
###############################################
# Uncomment these if you add JSON serialization later (e.g., Gson or Moshi)
# -keep class com.google.gson.** { *; }
# -dontwarn com.google.gson.**

###############################################
# 🚀 App-Specific
###############################################

# Keep your Application and ViewModels (so DI works)
-keep class com.d12.expirymonitor.**ViewModel { *; }
-keep class com.d12.expirymonitor.MyApplication { *; }

# Keep utils and extensions used by Room or Compose
-keep class com.d12.expirymonitor.utils.** { *; }

###############################################
# ✅ Suppress Common Warnings
###############################################
-dontwarn java.lang.invoke.*
-dontwarn org.intellij.lang.annotations.*
-dontwarn kotlin.**
-dontwarn androidx.lifecycle.**
-dontwarn androidx.activity.**
-dontwarn androidx.savedstate.**



# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn java.awt.Component
-dontwarn java.awt.GraphicsEnvironment
-dontwarn java.awt.HeadlessException
-dontwarn java.awt.Window


