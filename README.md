# ⏱️ TimerZeer KMP — Kotlin Multiplatform Timer (Android + iOS)

A **cross-platform countdown & stopwatch app** built with **Kotlin Multiplatform (KMP)**.  
It shares timer logic between Android and iOS using `StateFlow`, and integrates with **ActivityKit** on iOS to show real-time countdowns on the **Lock Screen** and **Dynamic Island**.

---

## 🚀 Tech Stack

| Layer | Technology |
|-------|-------------|
| **Shared Code** | Kotlin Multiplatform (KMP), Coroutines, Flow |
| **Android** | Jetpack Compose |
| **iOS** | SwiftUI, ActivityKit, WidgetKit |
| **Interop** | KMP Native Coroutines (`com.rickclephas.kmp.nativecoroutines`) |
| **DI** | Koin |
| **Language Level** | Kotlin 2.x, Swift 6 concurrency |

---

## 🎯 Features

- ⏳ Countdown timer & ⏱ Stopwatch  
- ⏸ Pause / ▶ Resume / ⏹ Stop support  
- 🔁 Real-time synchronization between KMP & SwiftUI  
- 📲 iOS Live Activity (Dynamic Island + Lock Screen)  
- 🧩 Shared StateFlow observed in Swift via `asyncSequence(for:)`  
- 💾 Local persistence via `TimerPersistence`  
- 💡 Millisecond-accurate pause/resume sync  

---

## 🧩 Architecture Overview

```
┌────────────────────────────┐
│        Compose UI          │
│         (Android)          │
└──────────────┬─────────────┘
               │
               ▼
┌────────────────────────────┐
│     Shared KMP Module      │
│ ─ TimerRepository.kt       │
│ ─ TimerState (StateFlow)   │
│ ─ TimerController expect/actual │
│ ─ TimerPersistence expect/actual│
└──────────────┬─────────────┘
               │
               ▼
┌────────────────────────────┐
│          iOS App           │
│  (SwiftUI + ActivityKit)   │
│  LiveActivityManager.swift │
│  TimerWidget.swift         │
└────────────────────────────┘
```

---

## 🧠 Key Concepts

### 🕓 Shared Timer Logic (`TimerRepository.kt`)
```kotlin
val elapsed = if (_timerState.value.mode == TimerMode.STOPWATCH) {
    initialMillis + (currentTimeMillis() - start)
} else {
    max(initialMillis - (currentTimeMillis() - start), 0L)
}
_timerState.update { it.copy(elapsedTime = elapsed) }
```

---

### 🍎 iOS Live Activity Integration (`LiveActivityManager.swift`)
```swift
let sequence = asyncSequence(for: repository.timerStateFlow)
for try await state in sequence {
    if state.isRunning {
        await self.updateActivity(
            elapsedTime: state.elapsedTime,
            mode: state.mode.name
        )
    }
}
```

Pausing and resuming are synchronized with millisecond accuracy to avoid drift between platforms.

---

## 📦 Setup

### 🧱 Prerequisites

- Android Studio Koala+ (KMP-ready)
- Xcode 15+
- Kotlin 2.x
- Swift 6 (with strict concurrency)
- iOS 17+ for Live Activity support

### 🧭 Clone the project

```bash
git clone https://github.com/yourusername/TimerZeerKMP.git
cd TimerZeerKMP
```

---

## 🔧 Build & Run

### ▶ Android
```bash
# Open in Android Studio
# Select the `androidApp` run configuration
# Run on emulator or device
```

### 🍏 iOS
```bash
# Open `TimerZeerKMP.xcworkspace` in Xcode
# Choose a physical iPhone target (required for Live Activities)
# Run the app
# Start a timer → Live Activity appears on Lock Screen / Dynamic Island
```

---

## ⚙️ TimerController Bridge

### Shared expect declaration
```kotlin
expect interface TimerController {
    fun start(durationInSeconds: Long)
    fun pause()
    fun resume()
    fun stop()
}
```

### iOS actual implementation
```swift
public class LiveActivityManager: NSObject, @preconcurrency TimerController {
    @MainActor
    public func start(durationInSeconds: Int64) { ... }
    public func pause() { ... }
    public func resume() { ... }
    public func stop() { ... }
}
```

---

## 🧮 Precision Handling

| Issue                           | Cause                           | Fix                                          |
|---------------------------------|---------------------------------|----------------------------------------------|
| 1-second drift after pause      | rounding seconds                | store remaining time in **milliseconds**     |
| Initial lag                     | `delay(1.seconds)` before start | remove it                                    |
| Countdown vs Stopwatch mismatch | wrong elapsed calculation       | use `max(initial - delta, 0L)` for countdown |

---

## 🧘 Future Enhancements

- ☄️Firebase notification handling
- 📆Data picker for iOS
- 🔔 Notifications when countdown ends  


---


## Screen Shots
| Android                                                                                                               | IOS                                                                                                                   | Live Activity                                                                                                         |
|-----------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|
| <img width="300" alt="image" src="https://github.com/user-attachments/assets/a9e1e4c9-d6a9-465d-aa7b-e3ff37ead2ef" /> | <img width="300" alt="image" src="https://github.com/user-attachments/assets/a04be75a-de0b-4931-b9bd-a869a1ee4c50" /> | <img width="300" alt="image" src="https://github.com/user-attachments/assets/a17f6347-b3d3-44db-89df-7c719be968ab" /> |

## 📄 License

```
MIT License

Copyright (c) 2025

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files...
```
