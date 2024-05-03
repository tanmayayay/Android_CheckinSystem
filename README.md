# Android Checkin System

## Introduction
The Android Checkin System is a mobile application designed to enable users to check in at various locations using Google Maps. It supports check-ins within a predefined radius and keeps users marked as present at a location for a specified duration. The system is integrated with a backend service hosted on Google Cloud for robust data management.

## Features
- **Google Maps Integration**: Allows users to check-in at locations.
- **Active Check-ins**: View check-ins from other users within a specific radius.
- **User Profiles and Data Management**: Backend handles all user profiles and check-in data.

## Prerequisites
- Android Studio (Preferably the latest version)
- Google Cloud account
- Access to Google Maps API and Google Places API
- Java Development Kit (JDK)
- Kotlin (for Android development)

## Backend Service
The backend service is already hosted on Google Cloud and handles all interactions with the database and processes data from the mobile application.
- For backend details, visit the repository: [CheckinSystem_Backend](https://github.com/tanmayayay/CheckinSystem_Backend)

## Installation

### Clone the Repository
```bash
git clone https://github.com/tanmayayay/Android_CheckinSystem.git
cd Android_CheckinSystem

### Running the Application
1. Open the project in Android Studio.
2. Add the Google Cloud API Key.
3. Build the project using `Build -> Make Project`.
4. Run the application on an emulator or a physical device using `Run -> Run 'app'`.
