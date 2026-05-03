# KMP TV Shows Explorer

A cross-platform TV Shows Explorer application built with **Kotlin Multiplatform** and **Compose Multiplatform**, targeting Android and iOS. This app allows users to discover, search, and save their favorite TV shows using the TVMaze API.

## ✨ Features
- **Animated Splash Screen:** Professional entry with overshoot animations.
- **Show Discovery:** Paginated list of popular TV shows.
- **Deep Search:** Dedicated search tab with isolated state management.
- **Genre Filtering:** Browse shows by categories like Drama, Action, Comedy, etc.
- **Detailed Information:** Detailed show info including synopsis, ratings, and episode lists.
- **Offline Favorites:** Save shows locally to view them even without an internet connection.
- **Cross-Platform Navigation:** Unified navigation structure that supports platform-specific UI customizations.

## 🚀 Tech Stack
- **UI:** Compose Multiplatform (Jetpack Compose for both Android and iOS)
- **Dependency Injection:** Koin (v4.0.0)
- **Networking:** Ktor (v2.3.12) with Content Negotiation and JSON Serialization
- **Database:** SQLDelight (v2.0.2) for local caching and favorites
- **Image Loading:** Coil 3 (v3.0.0-rc01)
- **Logging:** Napier (v2.6.1)
- **Navigation:** Compose Navigation (v2.8.0-alpha10)
- **Architecture:** MVVM (Model-View-ViewModel) with Clean Architecture principles
- **Kotlin:** 2.1.21

## 📂 Project Structure
* **composeApp**: Main module containing Shared logic and UI.
    - `commonMain`: Shared ViewModels, Repositories, Data Sources, and **Common Screens** (Search, Categories, Favorites, About).
    - `androidMain`: Android-specific entry point and Material 3 Navigation implementation.
    - `iosMain`: iOS-specific entry point, ready for custom Cupertino UI implementation.
* **iosApp**: Native iOS wrapper that hosts the Compose Multiplatform UI.

## 🛠️ Getting Started

### Prerequisites
- Android Studio Ladybug or newer.
- Xcode 16.0+ (for iOS development).
- Kotlin Multiplatform Plugin.

### Build and Run

#### Android
- Use the `composeApp` run configuration in Android Studio.
- Or run: `./gradlew :composeApp:assembleDebug`

#### iOS
- Use the `iosApp` run configuration in Android Studio (requires a configured simulator).
- Or open the `iosApp` folder in Xcode and run the `iosApp` scheme.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
