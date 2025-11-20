# CookIT - Recipe Sharing Android App

CookIT is a modern Android application for discovering, sharing, and managing recipes. Built with Jetpack Compose and following Clean Architecture principles.

## 🏗️ Project Structure

The project follows Clean Architecture with clear separation of concerns:

```
app/src/main/java/com/example/cookit/
├── data/                    # Data layer
│   ├── network/            # API service definitions
│   └── repository/         # Repository implementations
├── di/                     # Dependency Injection modules
│   ├── NetworkModule.kt    # Network dependencies (Retrofit, OkHttp)
│   └── RepositoryModule.kt # Repository dependencies
├── domain/                 # Domain layer
│   └── repository/         # Repository interfaces
├── model/                  # Data models & DTOs
├── ui/                     # UI layer
│   ├── composables/        # Reusable UI components
│   ├── navigation/         # Navigation setup
│   ├── paging/             # Pagination sources
│   ├── screens/            # Feature screens
│   │   ├── addPost/        # Add recipe functionality
│   │   ├── auth/           # Authentication screens
│   │   └── home/           # Home & feed screens
│   └── theme/              # App theming
├── utils/                  # Utility classes & extensions
└── viewModel/              # ViewModels for UI state management
```

## 🛠️ Tech Stack

### Core
- **Kotlin** - Programming language
- **Jetpack Compose** - Modern UI toolkit
- **Material3** - Material Design components

### Architecture & DI
- **Hilt** - Dependency injection
- **MVVM** - Architecture pattern
- **Clean Architecture** - Separation of concerns

### Networking
- **Retrofit** - HTTP client
- **OkHttp** - Network interceptor
- **Gson** - JSON serialization

### Android Jetpack
- **Navigation Compose** - Navigation
- **Lifecycle** - Lifecycle-aware components
- **ViewModel** - UI state management
- **Paging 3** - Pagination support

### Image Loading
- **Coil** - Image loading library

### Other
- **Coroutines & Flow** - Asynchronous programming
- **SharedPreferences** - Local data storage

## 🏛️ Architecture

### Data Flow
```
UI Layer (Compose) → ViewModel → Repository (Domain) → Repository (Data) → API Service
```

### Key Principles
1. **Separation of Concerns**: Each layer has a specific responsibility
2. **Dependency Inversion**: Higher layers depend on abstractions, not concrete implementations
3. **Single Source of Truth**: ViewModels manage UI state
4. **Unidirectional Data Flow**: Data flows down, events flow up

## 🔧 Configuration

### Build Variants
- **Debug**: Development build with logging enabled
- **Release**: Optimized build with ProGuard/R8 enabled

### ProGuard/R8
Release builds are optimized with:
- Code minification
- Resource shrinking
- Obfuscation

### Gradle Optimizations
- Build cache enabled
- Parallel execution
- Configuration on demand
- R8 full mode for better optimization

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug or later
- JDK 11 or higher
- Android SDK 26 (minimum) - 36 (target)

### Setup
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run the app

### Backend Configuration
The app connects to: `https://cookit-backend-gj6e.onrender.com/api/`

To change the backend URL, update `Constants.BASE_URL` in:
```kotlin
app/src/main/java/com/example/cookit/utils/Constants.kt
```

## 📱 Features

- User authentication (login/register)
- Browse recipe feed with pagination
- Search and explore recipes
- User profiles and following system
- Create and share recipes
- Like recipes
- View recipe details with ingredients and steps

## 🔐 Security

- Token-based authentication
- Secure API communication with HTTPS
- ProGuard rules for release builds
- Sensitive data stored in SharedPreferences

## 🧪 Testing

### Running Tests
```bash
./gradlew test           # Unit tests
./gradlew connectedCheck # Instrumented tests
```

## 📦 Building

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

## 🤝 Contributing

1. Follow the existing code style
2. Use meaningful commit messages
3. Keep changes focused and atomic
4. Test your changes thoroughly

## 📄 Code Style

- Use Kotlin official code style
- Follow Android best practices
- Use meaningful variable and function names
- Add comments for complex logic

## 🔄 State Management

The app uses StateFlow for reactive state management:
- ViewModels expose StateFlow for UI state
- UI observes state changes and recomposes
- Loading, Success, and Error states handled consistently

## 🌐 API Integration

API calls are made using Retrofit with:
- Automatic token injection via interceptor
- Proper error handling
- Coroutines for asynchronous operations
- Type-safe API definitions

## 📊 Performance Optimizations

- LazyColumn for efficient list rendering
- Paging 3 for large datasets
- Coil for optimized image loading
- R8 for code optimization
- Resource shrinking for APK size reduction

## 📝 License

[Add your license here]

## 👥 Authors

- SurajM23

## 🙏 Acknowledgments

- Backend API provided by CookIT Backend
- Icons from Material Design and Font Awesome
