# Architecture

Football Groups follows **Clean Architecture** layered with **MVVM** in the presentation
layer.

```
┌──────────────────────────────────────────┐
│          app  (Presentation)             │
│  ┌────────────┐  ┌─────────────────────┐ │
│  │  Compose   │  │    ViewModel        │ │
│  │  Screens   │◄─│  (StateFlow / UI    │ │
│  └────────────┘  │   state)            │ │
│                  └──────────┬──────────┘ │
│                             │ invokes    │
├─────────────────────────────▼────────────┤
│          domain  (Business Logic)        │
│  ┌──────────┐  ┌──────────────────────┐  │
│  │  Models  │  │     Use Cases        │  │
│  └──────────┘  └──────────┬───────────┘  │
│                            │ calls        │
│  ┌─────────────────────────▼───────────┐  │
│  │  Repository Interfaces  (contracts) │  │
│  └─────────────────────────────────────┘  │
├───────────────────────────────────────────┤
│          data  (Infrastructure)           │
│  ┌──────────────────────────────────────┐ │
│  │  Room DB  ←  DAOs  ←  Entities      │ │
│  │  Repository Implementations          │ │
│  │  Mappers (entity ↔ domain model)     │ │
│  └──────────────────────────────────────┘ │
└───────────────────────────────────────────┘
```

## Key Principles

### Dependency Rule
Dependencies point **inward only**:
- `app` depends on `domain` and `data`.
- `data` depends on `domain`.
- `domain` has **zero** Android dependencies.

### Why MVVM?
- ViewModels survive configuration changes.
- `StateFlow` gives a single source of truth per screen.
- Screens are purely declarative — they react to state, never hold it.

### Why Use Cases?
Each use case is a **single-responsibility class** that encodes one piece of business logic
(e.g. `ConfirmParticipationUseCase`). This makes testing trivial and keeps ViewModels thin.

### Why Hilt?
Constructor injection + Hilt modules provide compile-time safety, clear scoping, and easy
swap of implementations for tests.

### Why Room + Flow?
Room's `Flow` return type means the UI re-renders automatically when any row changes —
no polling, no manual refresh.

## Adding a New Feature

1. **Add a domain model** in `domain/model/` if needed.
2. **Extend the repository interface** in `domain/repository/`.
3. **Add a use case** in `domain/usecase/`.
4. **Implement the data layer** (entity, DAO query, mapper, repository impl).
5. **Update the Hilt modules** in `app/di/`.
6. **Add a ViewModel** and **Compose screen**.
7. **Register the route** in `Screen.kt` and `NavGraph.kt`.
