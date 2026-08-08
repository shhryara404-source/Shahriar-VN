# Shahriar VN

Native Android personal identity app for Shahriar.

## Stack
- Kotlin
- Jetpack Compose
- Material 3
- Android API 26+
- GitHub Actions automated APK build

## Product direction
A cinematic, design-forward personal identity app combining biography, interests, projects, Nemoris worldbuilding, ideas, library, and contact in a bilingual Persian/English experience.

## Screens
Home, About, Skills, Projects, Nemoris, Ideas, Library, Contact.

## Build
The repository is configured so GitHub Actions installs Gradle 8.8 and builds `assembleDebug` without requiring a committed Gradle wrapper JAR.

## Assets
The app is wired to `hero_cartoon` and `portrait_real` drawable resources. The repository currently contains lightweight vector fallback artwork for those resource names so the native build remains self-contained; the original supplied raster portraits can be dropped into the same paths later without changing Kotlin code.

## Package
`com.shahriar.vn`
