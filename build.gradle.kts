// Root build file — configuration shared across all modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.detekt) apply true
}

// ── Detekt — applied to all subprojects from the root ────────────────────────
detekt {
    buildUponDefaultConfig = true
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    source.setFrom(
        files(
            "$rootDir/app/src/main/java",
            "$rootDir/domain/src/main/java",
            "$rootDir/data/src/main/java"
        )
    )
    parallel = true
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${libs.versions.detekt.get()}")
}
