# 📍 Business Directory App

An Android application built with Kotlin that serves as a business directory — displaying companies organized by category, with the ability to add new companies and get notified when you're near one.

---

## 📋 Features

- 📂 **4 Categories** — Services, Fun, Industry, Education
- 📋 **ListView** display for each category with company name, address, phone, and website
- 🔍 **Search** bar to filter companies by name within the active tab
- ➕ **Add Company** form with full details and multi-category checkbox selection
- ☁️ **Supabase** remote database for storing and retrieving companies
- 📍 **Geolocation** — notifies you with a Toast message when you are within 50 meters of a company
- 🔄 **Swipe navigation** between tabs

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Kotlin | Main programming language |
| Android Studio | Development environment |
| Supabase (PostgreSQL) | Remote cloud database |
| Ktor | HTTP client for Supabase |
| Google Play Services Location | GPS / Geolocation |
| TabLayout + ViewPager | Tab navigation |
| ListView + Custom Adapter | Company list display |
| Coroutines | Async operations |

---

## 📱 Screens

### Main Screen
- App bar with title and add button
- 4 tabs: Services, Fun, Industry, Education
- List of companies per tab
- Search bar at the bottom

### Add Company Screen
- Fields: Name, Address, Latitude, Longitude, Email, Telephone, Website
- Category checkboxes (multiple selection allowed)
- Save button → stores data in Supabase

---

## 🗄️ Database

This app uses **Supabase** (PostgreSQL) as a remote database.

Table: `companies`

| Column | Type |
|---|---|
| id | int8 (PK, auto-increment) |
| name | text |
| address | text |
| latitude | float8 |
| longitude | float8 |
| email | text |
| telephone | text |
| website | text |
| is_industry | bool |
| is_fun | bool |
| is_education | bool |
| is_services | bool |

---

## ⚙️ Setup

1. Clone the repository
2. Open in Android Studio
3. Create a free project at [https://supabase.com](https://supabase.com)
4. Create the `companies` table with the columns above
5. Open `data/SupabaseClient.kt` and replace:
```kotlin
supabaseUrl = "YOUR_PROJECT_URL"
supabaseKey = "YOUR_ANON_KEY"
```
6. Build and run on Android device (API 24+)

---

## 📦 Permissions

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```
