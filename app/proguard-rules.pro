# Add project-specific ProGuard rules here.
# Preserve Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ActivityComponentManager { *; }
# Preserve Room
-keep class * extends androidx.room.RoomDatabase { *; }
-dontwarn androidx.room.**
