# Todoora  
_Your personal task manager, built with Jetpack Compose for easy and efficient task management._

---

## Project Overview
**Todoora** is a modern, feature-rich To-Do app built using **Jetpack Compose** for Android. It provides a simple and intuitive interface to manage your daily tasks, helping you stay organized and productive. With **Todoora**, you can create tasks, assign priorities, set deadlines, and even sync tasks across multiple devices using **Firebase Firestore**.

Whether you're managing personal or work-related tasks, Todoora ensures everything is in one place and easy to track.

---

## Features
- Create, edit, and delete tasks effortlessly.
- Mark tasks as completed or pending.
- Prioritize tasks with different levels of importance (Low, Medium, High).
- Set due dates and receive reminders for upcoming tasks.
- Synchronize tasks to the cloud with **Firebase Firestore**, allowing access across multiple devices.
- Offline support for managing tasks when not connected to the internet, with real-time syncing once online.
- User authentication via **Firebase Auth** to secure and personalize task data.
- Search and filter tasks based on status, due date, or priority.

---

## Technologies Used
- **Kotlin**: The programming language for modern Android development.
- **Jetpack Compose**: Declarative UI framework for Android.
- **Room Database**: Local database for offline storage and management of tasks.
- **Firebase Firestore**: Cloud database for task synchronization across devices.
- **Firebase Authentication**: Secure login and authentication for users.
- **ViewModel + LiveData/StateFlow**: For handling UI data in a lifecycle-aware manner.
- **Coroutines/Flow**: For managing asynchronous operations such as cloud syncing.

---

## Installation Instructions

To set up and run the Todoora project locally:

1. Clone the repository:
   ```bash
   git clone https://github.com/george-killua/Todoora.git
2. Open the project in Android Studio.
3. Ensure the latest version of Android Studio is installed, with Kotlin and Jetpack Compose support.
4. Sync the Gradle files to download necessary dependencies.
5. Set up Firebase:
   * Add your `google-services.json` file to the `app/` directory.
   * Configure Firestore and Firebase Authentication from the Firebase console.
6. Build and run the app on a connected Android device or emulator.

---

## License

his project is licensed under the MIT License - see the [LICENSE](LICENSE) file for more details.
