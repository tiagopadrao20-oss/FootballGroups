# Football Groups 🏆⚽

An Android app for groups of friends to schedule football (soccer) matches.

## Features

- **Groups** — Create groups, invite friends by name, view member lists.
- **Events** — Create match events with date, time, location, and player limit.
- **Participation** — Members confirm or cancel their spot; the event shows live availability.
- **History** — Past events are preserved and displayed separately.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 1.9 |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| DI | Hilt |
| Database | Room |
| Async | Coroutines + Flow |
| Navigation | Navigation Compose |

## Module Structure

```
FootballGroups/
├── app/          # Android app module — UI, ViewModels, DI, Navigation
├── domain/       # Pure Kotlin — Models, Repository interfaces, Use Cases
├── data/         # Android — Room entities, DAOs, Repository implementations
└── docs/         # Documentation
```

## Quick Start

See **[docs/USER_GUIDE.md](docs/USER_GUIDE.md)** for full setup instructions.

```bash
# Clone
git clone https://github.com/<your-username>/FootballGroups.git

# Open in Android Studio / VS Code with Android extension
# Sync Gradle → Run on device or emulator
```

## Architecture

See **[docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)** for a detailed explanation.

## Data Models

See **[docs/DATA_MODELS.md](docs/DATA_MODELS.md)** for entity/schema documentation.

## License

MIT
