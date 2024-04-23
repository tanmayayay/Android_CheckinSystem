Android Checkin System
Introduction
The Android Checkin System is a mobile application designed to allow users to check in to various locations using Google Maps. This system supports check-ins within a predefined radius and displays users as present in a location for a specified duration. This project is integrated with a backend service hosted on Google Cloud to handle all data management and interaction.

Features
Check-in to locations using Google Maps.
View active check-ins from other users within a specific radius.
Backend management of user profiles and check-in data.
Email notifications through integration with SendGrid.
Prerequisites
Android Studio
Google Cloud account
Access to Google Maps API and Google Places API
Java Development Kit (JDK)
Backend Service
The backend service for this application is located in a separate repository: CheckinSystem_Backend. This service handles all interactions with the database and processes all data sent from the mobile app.

Installation
Clone the Repository
First, clone the repository to your local machine:

bash
Copy code
git clone https://github.com/tanmayayay/Android_CheckinSystem.git
cd Android_CheckinSystem
Set Up Backend
Ensure that the backend service is running by cloning its repository and following the setup instructions there:

bash
Copy code
git clone https://github.com/tanmayayay/CheckinSystem_Backend.git
cd CheckinSystem_Backend
# Follow the backend setup instructions
Configure Google Cloud Database
Set up a MySQL instance on Google Cloud SQL.
Update application.properties with your Cloud SQL instance details and credentials.
Ensure proper configurations are set for JDBC connection string.
Running the Application
Open the project in Android Studio.
Build the project using Build -> Make Project.
Run the application on an emulator or physical device using Run -> Run 'app'
