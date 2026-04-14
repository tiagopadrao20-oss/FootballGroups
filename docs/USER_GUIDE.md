# User Guide

## Prerequisites

| Tool | Version | Install |
|---|---|---|
| JDK | 17+ | https://adoptium.net |
| Android Studio | Hedgehog+ | https://developer.android.com/studio |
| Android SDK | API 34 | Via SDK Manager in Android Studio |
| VS Code (optional) | Latest | https://code.visualstudio.com |

---

## 1. Install Dependencies

### Android Studio (recommended)
1. Download and install Android Studio.
2. Open **SDK Manager** → install **Android 14 (API 34)** SDK platform.
3. Install a system image for an AVD (emulator), e.g. *Pixel 6 API 34*.

### VS Code
1. Install the **Kotlin** extension (`mathiasfrohlich.Kotlin`).
2. Install the **Android iOS Emulator** extension for running the emulator from VS Code.
3. Use the integrated terminal for all Gradle commands.

---

## 2. Open the Project

### Android Studio
```
File → Open → select the FootballGroups/ folder
```
Wait for Gradle sync to finish (first sync downloads ~500 MB).

### VS Code
```
File → Open Folder → select FootballGroups/
```
Open `settings.gradle.kts` to confirm the project root. Use the terminal for builds.

---

## 3. Build and Run

### From Android Studio
1. Connect a physical device (enable USB Debugging) **or** start an AVD.
2. Select the device in the toolbar.
3. Click **Run ▶** or press `Shift + F10`.

### From terminal / VS Code
```bash
# Debug build + immediate install on connected device
./gradlew installDebug

# Just build the APK
./gradlew assembleDebug
# APK path: app/build/outputs/apk/debug/app-debug.apk
```

### Install APK manually on a phone
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 4. Using the App

### Creating a Group
1. On the **Groups** home screen tap **New Group**.
2. Enter a group name (required), optional description, and your name.
3. Tap **Create Group** — you are taken to the group's detail screen.

### Adding Members
1. Open a group.
2. Tap the **person+ icon** in the top bar.
3. Enter the player's name and tap **Add**.

### Creating a Match Event
1. Open a group.
2. Tap the **+ FAB** (bottom-right).
3. Fill in title, location, max players, your name, and adjust the date/time.
4. Tap **Create Event**.

### Confirming Participation
1. Open an event from the group detail screen.
2. Tap **Join** (visible while the event is upcoming and not full).
3. Enter your name and tap **Confirm** — your name appears in the participants list.

### Cancelling Participation
1. Open the event.
2. Tap the **X** button next to your name in the participants list.

---

## 5. Running Tests

```bash
# Unit tests (domain logic)
./gradlew :domain:test

# All tests
./gradlew test
```

---

## 6. Build a Release APK

1. Generate a signing keystore (once):
   ```bash
   keytool -genkeypair -v -keystore football-groups.jks -alias footballgroups \
     -keyalg RSA -keysize 2048 -validity 10000
   ```
2. Add signing config to `app/build.gradle.kts`.
3. Build:
   ```bash
   ./gradlew assembleRelease
   ```
