# Pixabay Photo Viewer

Pixabay Photo Viewer is a modern Android application that allows users to browse and view images fetched from the Pixabay API. The app is built with a clean architecture, follows Material Design principles, and supports both light and dark modes.

---

## **Features**
- **User Authentication**: Login and registration with basic validation.
  - Login credentials:
    - **Email**: `test@test.com`
    - **Password**: `password123`
- **Browse Images**: Fetches images from the Pixabay API with pagination support.
- **Image Details**: Displays detailed information about the selected image.
- **Dark Mode Support**: The app UI adapts to the system's light or dark theme.
- **Secure Storage**: Credentials are securely stored using encrypted shared preferences.

---

## **Technologies Used**
### **Languages and Frameworks**
- **Kotlin**
- **Coroutines** and **Flow**
- **Jetpack Components**: ViewModel, LiveData, Navigation Component, and Data Binding

### **Architectural Patterns**
- **Clean Architecture**: Layers separated into `presentation`, `domain`, and `data`.
- **MVVM** (Model-View-ViewModel)

### **Third-Party Libraries**
- **Retrofit**: For network requests
- **OkHttp**: For HTTP client
- **Hilt**: For dependency injection
- **Glide**: For image loading
- **MockWebServer**: For testing API responses
- **Google Truth**: For assertions in test cases
- **Espresso**: For UI testing
- **Mockito**: For mocking dependencies

---

## **Installation**

### **Prerequisites**
- Android Studio (Latest version recommended)
- Minimum SDK: 21 (Android 5.0 Lollipop)
- Target SDK: 33 (Android 13)

### **Steps to Build**
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/pixabay-photo-viewer.git
   cd pixabay-photo-viewer
2. Open the project in **Android Studio**.
3. Sync the Gradle files.
4. Replace the API key in `BuildConfig` with your **Pixabay API key**:
   ```kotlin
   const val API_KEY = "YOUR_API_KEY_HERE"
5. Run the app on an emulator or device.

## **How to Use**
1. **Login**:
   - Use the provided credentials:
     - **Email**: `test@test.com`
     - **Password**: `password123`
2. **Register**:
   - Create a new user by providing an email, password, and age (18–99).
3. **Browse Images**:
   - Scroll through a list of images retrieved from the Pixabay API.
4. **View Image Details**:
   - Tap an image to view details like:
     - Image size, type, tags, uploader info, views, likes, comments, and more.
5. **Logout**:
   - Use the dropdown menu in the toolbar to log out.


