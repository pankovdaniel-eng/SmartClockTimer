# Smart Clock Android App

This is a basic Android application that displays a digital clock and allows you to set a wake-up alarm, with some smart features based on the weather.

## Features

*   Displays the current time.
*   Fetches and displays the current weather for your location.
*   Allows setting a one-time alarm.
*   Shows a notification when the alarm goes off.
*   **Smart Postponement**: Automatically postpones the alarm for 10 minutes if it's raining, snowing, or the temperature is below -15°C.

## How to Build and Run

### Prerequisites

*   Android Studio installed on your machine.
*   An Android device or emulator.

### Steps

1.  **Open the project in Android Studio:**
    *   Open Android Studio.
    *   Click on "Open" or "Open an Existing Project".
    *   Navigate to the root directory of this project and select it.

2.  **Sync the project with Gradle:**
    *   Android Studio should automatically start syncing the project with Gradle. If not, click on the "Sync Project with Gradle Files" button in the toolbar.

3.  **Run the app:**
    *   Select an Android device or emulator from the dropdown menu in the toolbar.
    *   Click on the "Run 'app'" button (the green play button) in the toolbar.

The app should now build, install, and run on your selected device or emulator, displaying a digital clock in the center of the screen.

### Command-line build (alternative)

You can also build the project from the command line using Gradle.

### Initial Gradle Wrapper Setup

The `gradle-wrapper.jar` file is not included in this repository. The first time you build the project, the Gradle wrapper will download the correct Gradle distribution and the `gradle-wrapper.jar` file. You can trigger this by running the following command in your terminal from the root of the project:

```bash
./gradlew --version
```
or on Windows:
```bash
gradlew.bat --version
```

This will download and set up Gradle. After this initial setup, you can proceed with building the project.

### API Key Setup

This app uses the OpenWeatherMap API to fetch weather data. You will need to get a free API key from [OpenWeatherMap](https://openweathermap.org/api) and add it to the project.

1.  Open the file `app/src/main/res/values/strings.xml`.
2.  Find the line `<string name="openweathermap_api_key">YOUR_API_KEY</string>`.
3.  Replace `YOUR_API_KEY` with your actual API key.

### Building the Project

1.  Navigate to the root directory of the project in your terminal.
2.  Build the debug APK:
    ```bash
    ./gradlew assembleDebug
    ```
3.  The APK will be located in `app/build/outputs/apk/debug/app-debug.apk`. You can install this on a device using `adb`:
    ```bash
    adb install app/build/outputs/apk/debug/app-debug.apk
    ```
