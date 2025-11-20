# Contributing to CookIT

Thank you for your interest in contributing to CookIT! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

- Be respectful and considerate
- Provide constructive feedback
- Focus on what is best for the community
- Show empathy towards other community members

## Getting Started

### Prerequisites
- Android Studio Ladybug or later
- JDK 11 or higher
- Git
- Basic knowledge of Kotlin and Jetpack Compose

### Setting Up Development Environment

1. **Fork the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/cookit-frontend.git
   cd cookit-frontend
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory
   - Wait for Gradle sync to complete

3. **Create a new branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

## Development Workflow

### Branch Naming Convention
- `feature/` - New features
- `fix/` - Bug fixes
- `refactor/` - Code refactoring
- `docs/` - Documentation changes
- `test/` - Test additions or modifications

Examples:
- `feature/add-recipe-rating`
- `fix/login-crash`
- `refactor/repository-layer`

### Commit Message Guidelines

Follow the conventional commits specification:

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation only
- `style`: Code style changes (formatting, missing semicolons, etc.)
- `refactor`: Code refactoring
- `perf`: Performance improvements
- `test`: Adding or updating tests
- `chore`: Build process or auxiliary tool changes

**Examples:**
```
feat(auth): add forgot password functionality

Implement forgot password feature with email verification
and password reset flow.

Closes #123
```

```
fix(recipes): resolve image loading crash

Fix null pointer exception when recipe image URL is empty.
Add fallback placeholder image.

Fixes #456
```

## Code Style

### Kotlin Style Guide
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Keep functions small and focused
- Prefer immutability (`val` over `var`)
- Use data classes for model objects

### Compose Guidelines
- Extract reusable composables
- Hoist state when possible
- Use remember and rememberSaveable appropriately
- Provide preview functions for composables
- Follow Material Design guidelines

### Architecture Guidelines
- Follow Clean Architecture principles
- Maintain separation of concerns
- Keep ViewModels free of Android dependencies
- Use dependency injection with Hilt
- Create repository interfaces in domain layer

## Testing

### Writing Tests
```kotlin
@Test
fun `test should verify expected behavior`() {
    // Arrange
    val input = "test"
    
    // Act
    val result = function(input)
    
    // Assert
    assertEquals(expected, result)
}
```

### Running Tests
```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

## Pull Request Process

### Before Submitting
1. ✅ Ensure your code follows the style guidelines
2. ✅ Update documentation if needed
3. ✅ Add or update tests for your changes
4. ✅ Run tests and ensure they pass
5. ✅ Update CHANGELOG.md if applicable
6. ✅ Rebase on the latest main branch

### Submitting a Pull Request
1. Push your branch to your fork
   ```bash
   git push origin feature/your-feature-name
   ```

2. Open a pull request on GitHub
   - Provide a clear title and description
   - Reference any related issues
   - Add screenshots for UI changes
   - List breaking changes if any

3. Wait for code review
   - Address reviewer comments
   - Push additional commits if needed
   - Request re-review when ready

### Pull Request Template
```markdown
## Description
Brief description of the changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
Describe how you tested your changes

## Screenshots (if applicable)
Add screenshots for UI changes

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Comments added for complex code
- [ ] Documentation updated
- [ ] Tests added/updated
- [ ] All tests passing
```

## Code Review Guidelines

### For Authors
- Be open to feedback
- Respond to comments promptly
- Ask questions if unclear
- Make requested changes or provide reasoning

### For Reviewers
- Be constructive and respectful
- Explain the reasoning behind suggestions
- Approve when satisfied
- Suggest alternatives when applicable

## Project Structure

Refer to [ARCHITECTURE.md](ARCHITECTURE.md) for detailed information about:
- Project architecture
- Layer responsibilities
- Dependency flow
- Best practices

## Common Tasks

### Adding a New Screen
1. Create composable in `ui/screens/`
2. Add route in `NavigationConstants`
3. Register in `NavGraph`
4. Create ViewModel if needed
5. Update navigation flows

### Adding a New API Endpoint
1. Add function to `ApiService`
2. Add function to repository interface
3. Implement in repository
4. Add function to ViewModel
5. Update UI to use new function

### Adding a New Dependency
1. Add to `gradle/libs.versions.toml`
2. Add to `app/build.gradle.kts`
3. Update ProGuard rules if needed
4. Document usage in code

## Resources

### Documentation
- [Android Developer Guide](https://developer.android.com)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)

### Learning Resources
- [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [MVVM Pattern](https://developer.android.com/topic/architecture)

## Getting Help

### Questions or Issues?
- Check existing issues on GitHub
- Read the documentation
- Ask in pull request comments
- Create a new issue with details

### Found a Bug?
1. Check if already reported
2. Create a new issue with:
   - Clear title
   - Steps to reproduce
   - Expected vs actual behavior
   - Screenshots if applicable
   - Device/Android version

## Recognition

Contributors will be recognized in:
- README.md contributors section
- Release notes for significant contributions
- GitHub contributors page

Thank you for contributing to CookIT! 🎉
