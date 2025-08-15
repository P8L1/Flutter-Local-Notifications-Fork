# 🧠 Agent Coding Rules & Project Context

This document outlines the required coding standards and project setup guidelines for agents contributing to this project.

---

## ✅ Code Quality Rules

1. **Production-Ready Only**

   - All code must be **production-grade** and ready for deployment.
   - No additional edits should be necessary.

2. **No Placeholders**

   - Do **not** use placeholders without explicit confirmation.

3. **PEP 8 Compliance**

   - When writing Django/Python, **always** follow [PEP 8](https://peps.python.org/pep-0008/) standards.
   - Code must be **readable, structured, and easy to debug**.

4. **Control Flow Restrictions**

   - Avoid **recursion** unless absolutely necessary.
   - Use **simple and bounded** control structures only.
   - All loops must have a **fixed upper bound**.

5. **Function Guidelines**

   - Functions must be short enough to print on a **single A4 page** using one line per declaration/statement.
   - Maintain **at least two assertions per function**.

6. **Scope Discipline**

   - Declare all data objects at the **smallest possible scope**.

7. **Return and Parameter Validation**

   - Calling functions must **check all return values** from non-void functions.
   - All parameters must be **validated** in the called function.

8. **Self-Review**

   - Always **evaluate the quality** of your response.
   - Rate your solution on a **scale of 1 to 10**.

9. **Ask Questions**

   - If anything is unclear, **ask questions until 100% confident**.
   - **Break down** complex tasks into small, clear steps.
   - Point out **any contradictions** in the prompt.

10. **Post-Code Analysis**
    - After delivering code, **explain its limits and strengths**.
    - Include notes on **scalability**, e.g., for handling **1M users**.

---

## Flutter Specific Rules

## ✅ Code Quality & Style

1. **Follow Dart Style Guide**

   - Use `dart format` and `dart analyze` to ensure formatting and consistency.
   - Use `const` constructors wherever possible.
   - Avoid wildcard imports (`import 'package:flutter/material.dart' as *`).

2. **Structure Code Cleanly**

   - Organize into layers: `data`, `domain`, `presentation`.
   - Group related files by feature, not by type.
   - Keep widgets small and composable (prefer composition over inheritance).

3. **Naming Conventions**

   - Use descriptive names: `LoginButton`, `UserTile`, `CaseDto`.
   - Follow camelCase for variables and PascalCase for class names.

4. **Avoid Logic in UI**
   - Keep widgets dumb. Business logic should go into providers, blocs, or services.

---

## 🧠 Architecture & State Management

1. **Use Riverpod (or Chosen State Management) Correctly**

   - Separate state (`StateNotifier`, `AsyncNotifier`, etc.) from UI.
   - Use `autoDispose` when appropriate to free up resources.
   - Keep side effects out of providers (no HTTP calls in UI).

2. **Avoid Over-Engineering**

   - Choose the simplest approach that satisfies the problem.
   - Prefer `StateNotifier` over `ChangeNotifier` for fine-grained control.

3. **Keep Widgets Stateless Where Possible**

   - Stateless widgets are easier to reason about and test.

4. **Minimize Rebuilds**
   - Use `const` constructors and `ConsumerWidget` or `ref.watch()` selectively.

---

## 🚀 Performance

1. **Use `const` as much as possible**

   - Reduces rebuilds and improves tree shaking.

2. **Avoid Unnecessary Rebuilds**

   - Use `Selector`, `Consumer`, or `ref.watch().select(...)` to watch specific slices of state.

3. **Defer Heavy Operations**

   - Use `Future.microtask` or `SchedulerBinding.instance.addPostFrameCallback` where appropriate.

4. **Use Lazy Loading**

   - For large lists, use `ListView.builder`, pagination, and `SliverList`.

5. **Minimize Use of `setState`**
   - Prefer state management for better control and testability.

---

## 🔌 Networking

1. **Use Retrofit or Dio**

   - Avoid raw `http` for production projects.
   - Define DTOs, error handling, and interceptors.

2. **Centralize API Definitions**

   - Group endpoints in `api_service.dart` or similar.

3. **Error Handling**
   - Never trust the backend blindly.
   - Wrap responses with `ApiResult<T>` or `Either<Failure, T>`.

---

## 🧪 Testing

1. **Write Unit Tests for Logic**

   - Cover providers, services, and model serialization.

2. **Widget Tests for UI**

   - Test rendering, interactivity, and state transitions.

3. **Use `mockito`, `mocktail`, or `fake_async`**

   - Mock external dependencies for isolated tests.

4. **CI Integration**
   - Run tests automatically on every pull request or merge.

---

## 🔐 Security

1. **Avoid Hardcoding Secrets**

   - Use `.env` files or encrypted storage.

2. **Validate All Inputs**

   - Client-side validation before submitting forms.

3. **Secure Local Storage**

   - Use `flutter_secure_storage` for storing tokens or sensitive data.

4. **Never Trust the Client Alone**
   - Always re-verify on the server.

---

## 🌐 Internationalization & Accessibility

1. **Use Flutter’s `intl` package**

   - Support multiple locales and number/date formats.

2. **Respect System Settings**

   - Honor text scale, theme mode, and accessibility gestures.

3. **Use Semantics**
   - Add meaningful labels for screen readers.

---

## 📦 Dependency Management

1. **Use Semantic Versioning in `pubspec.yaml`**

   - Lock versions for consistency across builds.

2. **Avoid Deeply Nested Dependencies**

   - Prefer simplicity and minimal external dependencies.

3. **Keep Dependencies Updated**
   - Use `flutter pub outdated` and test updates regularly.

---

## 🛠️ Tooling & Debugging

1. **Use Flutter DevTools**

   - For inspecting layouts, memory, performance, and logs.

2. **Log Verbosely (in Debug Only)**

   - Use a structured logger like `logger` package.

3. **Use `assert()` for Dev-Time Invariants**
   - Helps catch bugs early in development.

---

## 🔄 Lifecycle Management

1. **Handle App Lifecycle Events**

   - Listen to `WidgetsBindingObserver` for background syncs, etc.

2. **Dispose Resources**
   - Always dispose controllers, focus nodes, streams, etc.

---

## 🧠 Final Advice

- Build small, composable pieces.
- Always profile for performance.
- Write tests and document edge cases.
- Treat warnings and errors seriously.

---

Environment setup
flutter_local_notifications uses Melos to manage the monorepo project.

To install Melos, run the following command from a terminal/command prompt:

dart pub global activate melos
At the root of your locally cloned repository bootstrap the all dependencies and link them locally

melos bootstrap
This removes the need for providing manual dependency_overrides. There's no need to run flutter pub get either. All the packages, example app and tests will run for the locally cloned repository. The workflows setup on GitHub are also configured use Melos to validate changes. For more information on Melos, refer to its website

Some files to check are
flutter_local_notifications/android/src/main/java/com/dexterous/flutterlocalnotifications/FlutterLocalNotificationsPlugin.java flutter_local_notifications/android/src/main/java/com/dexterous/flutterlocalnotifications/models/NotificationDetails.java flutter_local_notifications/android/src/main/java/com/dexterous/flutterlocalnotifications/models/TitleStyle.java flutter_local_notifications/android/src/main/kotlin/com/dexterous/flutterlocalnotifications/TitleStyler.kt  
flutter_local_notifications/android/src/main/res/layout/fln_notif_title_only.xml
flutter_local_notifications/android/src/test/java/com/dexterous/flutterlocalnotifications/TitleStylerTest.kt  
flutter_local_notifications/lib/src/platform_specifics/android/notification_details.dart
