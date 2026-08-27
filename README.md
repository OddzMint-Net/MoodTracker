# Ugrand❓ 🙂

Ugrand? (formerly MoodTracker) is a Jetpack Compose Android app that helps users log their mood, reflect on their emotions, and receive AI-powered journaling prompts — built as a demonstration of production-grade Android architecture.

---
## ✨ Features

- Log moods with emoji-based mood selection
- Save mood history locally using Room, displayed as a live-updating history list
- AI-powered journaling prompts via the Gemini API, personalized using recent mood history
- Loading state and offline/local fallback messages when the AI call fails or is in progress
- Clean, single-activity Jetpack Compose UI (Material 3)


## 🔍 Architecture

Built with Clean Architecture principles, split into three independent layers:
presentation/   → Compose UI + ViewModels (Hilt-injected)
domain/         → Models, repository contracts, and use cases (no Android/Room/network dependencies)
data/           → Repository implementations, Room DAO/entities, Gemini network client, mappers

## 🎯 Key architectural decisions:

Repository pattern — domain defines contracts (MoodRepository, AiPromptRepository); data provides implementations. The rest of the app depends only on the contracts.
Use cases own business logic that spans repositories or encodes app-specific rules (e.g. LogMoodUseCase assigns the timestamp when a mood is logged; GetJournalingPromptUseCase fetches recent mood history before requesting an AI prompt).
Mappers convert between Room entities and domain models at the repository boundary — no persistence-layer types ever reach the ViewModel or UI.
Dependency Injection via Hilt — repositories, use cases, and the database are all provided through Hilt modules; ViewModels are injected with @HiltViewModel.

## 🛠️ Tech Stack
- Kotlin
- Jetpack Compose (Material 3)
- Clean Architecture (data / domain / presentation separation)
- MVVM with unidirectional state via StateFlow
- Hilt for dependency injection
- Room for local persistence
- Kotlin Coroutines & Flow
- Gemini API for AI-generated journaling prompts
- Gradle Kotlin DSL
- Privacy

Mood entries are stored locally on the device. The app does not use accounts, analytics, or cloud storage. Mood labels are sent to the Gemini API only to generate a journaling prompt; no other personal data leaves the device.

## ⚖️ License

This project was created by Odwa Mtatambi.