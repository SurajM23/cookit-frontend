#!/bin/bash

# Code Quality Check Script for CookIT
# Runs linting and static analysis

set -e

echo "🔍 Running lint checks..."
./gradlew lint

echo "📊 Generating lint report..."
echo "Report location: app/build/reports/lint-results.html"

echo "✅ Code quality check completed!"
