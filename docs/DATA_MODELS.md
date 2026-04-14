# Data Models

## Domain Models (Pure Kotlin — `domain/model/`)

### Group
| Field | Type | Description |
|---|---|---|
| id | Long | Auto-generated PK (0 = new) |
| name | String | Display name |
| description | String | Optional description |
| createdBy | String | Creator's name |
| createdAt | Long | Unix epoch ms |
| members | List\<Player\> | Loaded via JOIN |

### Player
| Field | Type | Description |
|---|---|---|
| id | Long | Auto-generated PK |
| name | String | Display name |
| groupId | Long | FK → Group.id |
| joinedAt | Long | Unix epoch ms |

### FootballEvent
| Field | Type | Description |
|---|---|---|
| id | Long | Auto-generated PK |
| groupId | Long | FK → Group.id |
| title | String | Short event title |
| dateTime | Long | Unix epoch ms |
| location | String | Venue or address |
| maxPlayers | Int | Capacity (2–50) |
| notes | String | Optional notes |
| createdBy | String | Organiser name |
| createdAt | Long | Unix epoch ms |
| participants | List\<EventParticipant\> | Loaded via JOIN |

### EventParticipant
| Field | Type | Description |
|---|---|---|
| id | Long | Auto-generated PK |
| eventId | Long | FK → FootballEvent.id |
| playerName | String | Player's name |
| confirmedAt | Long | Unix epoch ms |

---

## Room Schema

```
groups
  id INTEGER PK AUTOINCREMENT
  name TEXT NOT NULL
  description TEXT NOT NULL
  createdBy TEXT NOT NULL
  createdAt INTEGER NOT NULL

players
  id INTEGER PK AUTOINCREMENT
  name TEXT NOT NULL
  groupId INTEGER NOT NULL  → groups(id) ON DELETE CASCADE
  joinedAt INTEGER NOT NULL

events
  id INTEGER PK AUTOINCREMENT
  groupId INTEGER NOT NULL  → groups(id) ON DELETE CASCADE
  title TEXT NOT NULL
  dateTime INTEGER NOT NULL
  location TEXT NOT NULL
  maxPlayers INTEGER NOT NULL
  notes TEXT NOT NULL
  createdBy TEXT NOT NULL
  createdAt INTEGER NOT NULL

event_participants
  id INTEGER PK AUTOINCREMENT
  eventId INTEGER NOT NULL  → events(id) ON DELETE CASCADE
  playerName TEXT NOT NULL
  confirmedAt INTEGER NOT NULL
```

All timestamps are stored as Unix epoch milliseconds (Long) for simplicity and
timezone-neutral ordering.

## Schema Migrations

When you change the schema:
1. Bump `version` in `AppDatabase.kt`.
2. Add a `Migration(from, to)` object.
3. Pass it to `Room.databaseBuilder(...).addMigrations(MIGRATION_X_Y)`.
