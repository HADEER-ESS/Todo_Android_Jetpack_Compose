# Todo App – Jetpack Compose

An Android task manager built with **Kotlin** and **Jetpack Compose**. It uses a **ViewModel** to hold task state, lets you mark tasks as done and track progress, and connects to a custom **NestJS REST API** for authentication and task storage.

**Backend repository:** [nestjs-task-management](https://github.com/HADEER-ESS/nestjs-task-management)
**Live API:** https://YOUR-SUGA-URL

## Features

- Sign up and sign in with the backend API
- View, add, and delete tasks
- Mark tasks as completed and see your progress update
- State handled with `ViewModel`, so it survives configuration changes like screen rotation
- Fully declarative UI built with Jetpack Compose

## Tech Stack

- Kotlin
- Jetpack Compose (Material 3)
- ViewModel / state management
- REST API integration
- Backend: NestJS + PostgreSQL (Neon), deployed on Suga


## Screenshots

| Sign in | Task list | Add task |
|:-------:|:---------:|:--------:|
| <img src="https://github.com/user-attachments/assets/e4f7ecf0-1927-47c8-8e4e-082fa7a96fba" width="250" alt="Add task screen" /> | <img src="https://github.com/user-attachments/assets/1aa29ca3-a13b-4a17-bad5-44d6be5dc5aa" width="250" alt="Task list screen" /> |  <img src="https://github.com/user-attachments/assets/0bc18ad8-2f7c-4a8e-aa9e-d8e3e5996ab7" width="250" alt="Sign in screen" />|

## Getting Started

### Prerequisites

- Android Studio (latest stable version)
- JDK 17 or the version required by the project
- An Android emulator or a physical device (Android 7.0+)

### Run the app

```bash
git clone https://github.com/HADEER-ESS/Todo_Android_Jetpack_Compose.git
```

1. Open the project in Android Studio.
2. Wait for Gradle sync to finish.
3. Select a device or emulator and click **Run**.

### Configure the API base URL

The app talks to the deployed backend. The base URL is set in the app's network configuration:

```
https://YOUR-SUGA-URL/
```

To use a local backend instead, run the NestJS server on your machine and point the app at `http://10.0.2.2:3000/` (the Android emulator's alias for your computer's localhost). Plain HTTP also requires enabling cleartext traffic for debug builds.

> **Note:** The backend runs on free-tier hosting, and its database can sleep after inactivity. The first request after a quiet period may take a few seconds.

## Project Structure

```
app/
 └── src/main/java/.../
      ├── ui/           # Composable screens and components
      ├── viewmodel/    # ViewModels holding UI state
      ├── data/         # API calls and models
      └── MainActivity.kt
```

## Related Projects

- [nestjs-task-management](https://github.com/HADEER-ESS/nestjs-task-management): the REST API this app uses

## Author

**Hadeer Essam** · [GitHub](https://github.com/HADEER-ESS)
