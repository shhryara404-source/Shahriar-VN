# Shahriar VN

Native Android personal identity app for Shahriar.

## Phase 01 — Native Foundation

- Kotlin 2.0.21
- Jetpack Compose
- Material 3
- Android API 26+
- Application ID: `com.shahriar.vn`
- Dark/light theme foundation
- GitHub Actions debug APK pipeline

## Phase 03 — Identity & Hero

- Cinematic dark visual identity with gold/cyan accents
- Hero portrait wired to `hero_cartoon`
- Real portrait wired to `portrait_real`
- Shahriar VN hero treatment and personal identity hierarchy
- About/Identity screen
- Projects and Nemoris presentation cards
- Curiosity/Interests map
- Persian/English language switch with runtime RTL/LTR direction
- Refined page transitions and component styling
- No emoji in product UI

## Current screens

Home, About, Projects, Interests, Nemoris, Ideas, Library, Contact.

## Build

GitHub Actions is configured to build a debug APK on pushes to `main` and on manual dispatch.

## Next phases

1. Replace vector fallback artwork with the supplied raster portraits as repository binary assets
2. Finalize launcher/adaptive icon and splash assets
3. Complete bilingual content architecture and persistence
4. Complete all screen content and interactions
5. Motion and visual polish
6. Device QA and APK verification
7. Release signing and AAB
