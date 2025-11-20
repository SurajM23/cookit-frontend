#!/bin/bash

# Clean Build Script for CookIT
# This script performs a clean build of the project

set -e

echo "🧹 Cleaning project..."
./gradlew clean

echo "🔨 Building debug APK..."
./gradlew assembleDebug

echo "✅ Clean build completed successfully!"
echo "📦 APK location: app/build/outputs/apk/debug/"
