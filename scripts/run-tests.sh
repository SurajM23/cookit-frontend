#!/bin/bash

# Test Runner Script for CookIT
# Runs all tests (unit and instrumented)

set -e

echo "🧪 Running unit tests..."
./gradlew test

echo "📱 Running instrumented tests..."
echo "⚠️  Make sure an emulator is running or device is connected!"
./gradlew connectedAndroidTest

echo "✅ All tests completed!"
