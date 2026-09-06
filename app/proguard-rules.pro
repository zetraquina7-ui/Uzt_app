# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Preserve line numbers and source file names for stack trace debugging
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod,JavascriptInterface

# WebKit & JavaScript Interface (Crucial for YouTube player and Interactive Maps)
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keep class * implements android.webkit.ValueCallback { *; }

# Room Database rules
-keep class * extends androidx.room.RoomDatabase { *; }
-keep class com.example.data.** { *; }
-keep interface com.example.data.** { *; }
-dontwarn androidx.room.paging.**
-keepclassmembers class * extends androidx.room.RoomDatabase {
    <init>(...);
}

# Kotlinx Serialization (Generative AI & Network)
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.annotations.**
-dontnote kotlinx.serialization.builtins.**
-keepclassmembers class kotlinx.serialization.KSerializer { *; }
-keepclassmembers @kotlinx.serialization.Serializable class * {
    <init>(...);
}

# Retrofit rules
-keepattributes Signature, InnerClasses, EnclosingMethod
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-dontwarn retrofit2.**

# OkHttp & Okio
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class okio.** { *; }

# Moshi / JSON Serialization
-dontwarn javax.annotation.**
-keepclassmembers class * {
    @com.squareup.moshi.* <methods>;
    @com.squareup.moshi.* <fields>;
}
-keep class com.squareup.moshi.** { *; }
-keep class * extends com.squareup.moshi.JsonAdapter { *; }
-keep class * implements com.squareup.moshi.JsonAdapter$Factory { *; }
-keep @com.squareup.moshi.JsonClass class * { *; }

# Gson / Serializable fallback
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectStreamPrivateMethod);
    private void readObject(java.io.ObjectStreamPrivateMethod);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep app data entities and models
-keep class com.example.model.** { *; }
-keep class com.example.data.model.** { *; }
-keep class com.example.data.entity.** { *; }
-keep class com.example.util.VoiceResponse { *; }

# Kotlinx Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory { *; }
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler { *; }
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# Coil image loading
-keep class coil.** { *; }
-dontwarn coil.**

# Media3 & ExoPlayer
-keep class androidx.media3.** { *; }
-keep class androidx.media3.exoplayer.** { *; }
-keep class androidx.media3.common.** { *; }
-keep class androidx.media3.ui.** { *; }
-dontwarn androidx.media3.**
-keepclassmembers class * implements androidx.media3.common.Player$Listener { *; }

# Firebase & Google Services
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# Google Generative AI (Gemini)
-keep class com.google.ai.client.generativeai.** { *; }
-dontwarn com.google.ai.client.generativeai.**

# Avoid warnings for classes that we do not use or have dependencies for
-dontwarn org.bouncycastle.**
-dontwarn org.conscrypt.**
-dontwarn org.openjsse.**
-dontwarn org.apache.harmony.xnet.provider.jsse.**


