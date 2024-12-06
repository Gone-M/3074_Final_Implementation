# Personal Restaurant Guide

## Overview

The **Personal Restaurant Guide** is a user-friendly Android application designed to help users manage and explore their favorite dining experiences. This application allows users to add, view, and edit restaurant details, rate them, and search through their list of restaurants with ease. Integrated with Google Maps, it provides a seamless way to locate restaurants and get directions. The app emphasizes a clean design, offline usability, and smooth functionality.

---

## Features

### Core Functionalities

1. **Restaurant Management**
   - Add, edit, and delete restaurant details.
   - Fields include:
     - **Name**: Name of the restaurant.
     - **Address**: Properly formatted address.
     - **Phone Number(s)**: Contact information for the restaurant.
     - **Description**: User-entered notes about the restaurant.
     - **Rating**: Rate the restaurant using a 1-5 stars system.

2. **Search and Filter**
   - Instantly search through restaurants using keywords.

3. **Restaurant List and Details**
   - View a comprehensive list of all restaurants.
   - Tap a restaurant to see detailed information.

4. **Map Integration**
   - Display the restaurant's location on Google Maps.
   - Get directions from the current location to the restaurant.
   - View the user’s current location on the map.

5. **UI Design**
   - **Splash Screen**: Displays the app logo during startup.
   - **Card-Based Navigation**: Quick access to "Add Restaurant," "View Restaurants," and "About" sections.
   - **Immersive Fullscreen Mode**: Enhances the user experience by utilizing the full screen effectively.

---

## Technologies Used

1. **Frontend**
   - Android SDK
   - Material Design Components for an intuitive UI/UX.

2. **Backend**
   - SQLite for local data storage and retrieval.

3. **Maps Integration**
   - Google Maps API for location and navigation features.

4. **Programming Languages**
   - Java for Android development.

---

## Project Structure

### 1. **MainActivity**
   - Handles the primary user interface with search functionality and navigation cards.
   - Includes:
     - **RecyclerView** for displaying a list of restaurants.
     - **SearchBar** for filtering restaurants by name.
     - **Card-Based Navigation** for easy access to key features.

### 2. **MapActivity**
   - Displays a Google Map with restaurant and user locations.
   - Features include:
     - Current location tracking.
     - Adding restaurant markers on the map.
     - Camera zoom and movement for navigation.

### 3. **Restaurant**
   - A data model class representing restaurant information.
   - Fields:
     - `id`: Unique identifier.
     - `name`: Restaurant name.
     - `address`: Restaurant address.
     - `phone`: Contact number.
     - `description`: Additional details.
     - `rating`: User-provided rating (1-5).

### 4. **SQLiteHelper**
   - Manages local SQLite database operations.
   - Functions include:
     - Add, edit, delete, and retrieve restaurant data.
     - Search functionality based on name.

---

## Getting Started

### Prerequisites
1. **Android Studio** installed on your computer.
2. A device or emulator running Android 6.0 (Marshmallow) or later.
3. **Google Maps API Key** configured in the project.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository/personal-restaurant-guide.git
