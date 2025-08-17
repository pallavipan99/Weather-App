# Weather App — Android Application

A full-featured Android application that enables users to **get weather information** based on ZIP code, view current and hourly weather, toggle between Celsius and Fahrenheit units, and display weather icons. Built with Java, AsyncTask for background API calls, and OpenWeatherMap API integration.

---

## **Key Features**

- **ZIP Code Lookup:** Fetch weather data for any valid US ZIP code.
- **Weather Display:** Shows current and hourly temperatures, weather description, and icons.
- **Unit Toggle:** Switch between Celsius and Fahrenheit dynamically.
- **Async API Calls:** Fetch data in the background using AsyncTask.
- **Dynamic UI Updates:** UI updates in real-time as API responses are received.
- **Current Date Display:** Shows the current date and time in the app.

---

## **Tech Stack**

| Layer      | Technology                                         |
| ---------- | -------------------------------------------------- |
| Platform   | Android                                            |
| Language   | Java                                               |
| UI         | XML Layouts, TextView, ImageView, Button, EditText |
| API        | OpenWeatherMap API                                 |
| Networking | AsyncTask, URLConnection                           |

---

## **Project Structure**

- **MainActivity.java** — Main activity handling UI, user input, API calls, and weather display.
- **res/layout/activity\_main.xml** — Layout for the main activity including input fields, buttons, TextViews, and ImageViews.
- **res/drawable/** — Folder containing weather icons (e.g., sun, cloud, rain, snow).
- **AndroidManifest.xml** — App permissions and configuration.
- **build.gradle** — Project dependencies.

---

## **Quick Start**

1. Clone the project to your Android Studio workspace.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.
4. Enter a valid US ZIP code and press the **Location** button to fetch weather data.
5. Use the **Toggle Unit** button to switch between Celsius and Fahrenheit.

---

## **API Integration**

- This app uses the **OpenWeatherMap API** for weather and geolocation data.
- Users must replace the API key in the URL with their own key for proper functionality:

```java
"https://api.openweathermap.org/geo/1.0/zip?zip=" + zipC + ",us&appid=YOUR_API_KEY"
```

and

```java
"https://api.openweathermap.org/data/2.5/onecall?lat=" + lati + "&lon=" + longi + "&exclude=daily,minutely,alerts,current&units=" + units + "&appid=YOUR_API_KEY"
```

---

## **Usage Notes**

- **ZIP Code Input:** Must be a valid US ZIP code.
- **Weather Updates:** Hourly weather data is displayed for up to the first 4 hours.
- **Icons:** Weather description determines the icon (sun, cloud, rain, snow).
- **Unit Conversion:** Temperature unit toggles between Celsius and Fahrenheit instantly.
- **Error Handling:** Invalid ZIP codes will display a toast notification.

---

## **Future Improvements**

- Integrate more detailed weather forecasts (daily, weekly).
- Add location permission to automatically fetch user location.
- Use Retrofit or Volley for more robust networking.
- Implement persistent storage for favorite locations.
- Enhance UI/UX with Material Design components and animations.
- Include additional weather parameters like humidity, wind speed, and UV index.

---

## **Acknowledgements**

This project demonstrates Android development skills including:

- Handling background network calls with AsyncTask.
- Parsing JSON data from REST APIs.
- Dynamically updating UI elements based on API responses.
- Managing state and user interaction in an Android application.
- Integrating external APIs (OpenWeatherMap) into an Android app.

