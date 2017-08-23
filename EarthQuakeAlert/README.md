## About ##

As part of CSC 519 Android Application Development course, EarthQuakeAlert android app was developed for final term project.

**NOTE:** Alert in the app name is not a real alert about earthquake but influenced from an existing app in play store.


## Introduction ##

EarthQuakeAlert Android Application retrieves earthquake information from the free API available at https://earthquake.usgs.gov/ and displays it in user-readable formats like list view, map view and statistics view.

In order to demonstrate notifications, EarthQuakeAlert sends notification when the user changes his phone's airplane mode.

## How to Use? ##
  1. Clone the repository and import the project in Android Studio
  2. Get your google api key by following steps on https://developers.google.com/maps/documentation/android-api/signup
  3. Save your YOUR_API_KEY in AndroidManifest.xml under <br>
		    **meta-data <br>
				android:name="com.google.android.geo.API_KEY" <br>
				android:value="YOUR_API_KEY"** <br>
  4. Save your YOUR_API_KEY in gradle.properties as GOOGLE_MAPS_API_KEY=YOUR_API_KEY

## Summary ##

### Activities ###

EarthQuakeAlert has total 6 screens (views/activities) ...

  1. **MainActivity (Latest Earthquakes)** :
      - Displays the latest quakes information as per filters defined in settings page
	  - User can also refresh the data by click on refresh button in tool bar 
	  - Depending on the magnitude of the earthquake, the earthquake information is colored
	  - User can click on individual earthquake entry to get detailed information about the earthquake
      - From this screen user can go to ... 
	      - About
		  - Settings
		  - View Earthquakes
		  - Earthquake Statistics
		  - Earthquake Details

  2. **MapActivity (View Earthquakes)** :
      - Plots the earthquakes on google map as per filters defined in settings page
	  - User can also refresh the data by click on refresh button in tool bar
	  - Depending on the magnitude of the earthquake, the earthquake marker is colored
	  - User can click on individual earthquake marker to get high-level information about the earthquake
	  - User can click on individual earthquake info window to get detailed information about the earthquake
      - From this screen user can go to ... 
		  - Latest Earthquakes
		  - Settings

  3. **StatisticsActivity (Earthquake Statistics)** :
      - Displays the no. of earthquakes today, this week and this month as per filters defined in settings page
	  - User can also refresh the data by click on refresh button in tool bar 
      - From this screen user can go to ... 
		  - Latest Earthquakes
		  - Settings

  4. **DetailsActivity (Earthquake Details)** :
      - Displays the detailed information about the earthquake on Latest Earthquake screen
	  - Provides a link to USGS website for more information about the selected earthquake
      - From this screen user can only go back to Latest Earthquakes (MainActivity)

  5. **SettingsActivity (Settings)** :
      - Displays the following user settings
	      - EARTHQUAKE FILTERS
		      - Default Magnitude Filter
			      - 1.0+
				  - 2.0+
				  - 3.0+
				  - 4.0+
				  - 4.5+
				  - 5.0+
				  - 5.5+
				  - 6.0+
				  - 6.5+
				  - 7.0+
				  - 7.5+
              - Default Date Filter
			      - Last Hour
				  - Today
				  - Last 24 Hr
				  - This Week
		  - DATA CUSTOMIZATION
		      - Distance Units
			      - Miles
				  - Kilometers
      - From this screen user can go to the screen from where settings was selected.
	  - Settings are available on following pages ... 
	      - Latest Earthquakes
		  - View Earthquakes
		  - Earthquake Statistics

  6. **AboutActivity (About Author and App)** :
      - Displays information about author and android application
      - From this screen user can only go back to Latest Earthquakes (MainActivity)

### Notification ###

Whenever user changes his Airplane Mode (OFF to ON), EarthQuakeAlert sends a notification to the user that its been a while user has checked out the app for earthquake information. Clicking on notification would take the user to MainActivity.


### Database ###

Earthquake Alert stores the information about when the app is installed on the mobile device and when did the user last reviewed earthquake data in the app.

This information is displayed in about screen of the android app.

**NOTE:** Installed date is captured when the app is opened for the first time and not actual installation date.


### Color Codes ###

Depending on the magnitude of the earthquake, data in MainActivity and google markers in MapActivity are color coded as follows.

| Sr. No. | Magnitude | MainActivity Text Color | MapActivity Google Marker |
| ------- | --------- | ----------------------- | ------------------------- |
| 1. | 0.0 – 3.5 | #00FF00 | HUE_GREEN |
| 2. | 3.5 – 5.5 | #FF6347 | HUE_ORANGE |
| 3. | 5.5 – Above | #FF0000 | HUE_RED |


### Refresh Icon ###

All main screens i.e. MainActivity, MapActivity and StatisticsActivity have refresh icon in the tool bar, which refreshes data on the screen.


### Smart Footer ###

MainActivity and StatisticsActivity have smart footer, which displays the last updated time and filters set in settings.


## Screenshots ##

| Screen | ScreenShot |
| ------------- | ------------- |
| Launcher | <img src="https://user-images.githubusercontent.com/5839686/29504612-d55ee77a-85f5-11e7-9c2e-e77d712ac2a3.png" width="100"> |
| Navigation Drawer | <img src="https://user-images.githubusercontent.com/5839686/29504617-d5702e04-85f5-11e7-9c28-03b78ece8bf7.png" width="100"> |
| MainActivity | <img src="https://user-images.githubusercontent.com/5839686/29504615-d56b5cbc-85f5-11e7-85aa-9b73614fc25d.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504613-d56b01ea-85f5-11e7-9cdc-b61cbbd25061.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504614-d56b3d22-85f5-11e7-8909-bddd38ceee5b.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504616-d56cc7f0-85f5-11e7-9dc0-fd74c5007862.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504618-d5745132-85f5-11e7-8973-245574f60a5b.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504620-d580237c-85f5-11e7-8c5e-333de9c7f957.png" width="100"> |
| MapActivity | <img src="https://user-images.githubusercontent.com/5839686/29504619-d57fe01a-85f5-11e7-8204-691a0b13b1c6.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504622-d584e902-85f5-11e7-9d3b-c9308081113a.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504625-d5957240-85f5-11e7-9319-d06850010866.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504621-d5822f5a-85f5-11e7-85e7-d7c29ecf3060.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504623-d5887806-85f5-11e7-9c5d-b63157ada203.png" width="100"> |
| StatisticsActivity | <img src="https://user-images.githubusercontent.com/5839686/29504624-d5948f1a-85f5-11e7-9134-22ddc9f7a1e3.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504626-d596de64-85f5-11e7-988a-93b9d97fed32.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504629-d5a95e40-85f5-11e7-8bf0-7970056db102.png" width="100"> |
| DetailsActivity | <img src="https://user-images.githubusercontent.com/5839686/29504614-d56b3d22-85f5-11e7-8909-bddd38ceee5b.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504616-d56cc7f0-85f5-11e7-9dc0-fd74c5007862.png" width="100"> |
| SettingsActivity | <img src="https://user-images.githubusercontent.com/5839686/29504627-d59a58fa-85f5-11e7-800a-73755679bb11.png" width="100"> |
| AboutActivity | <img src="https://user-images.githubusercontent.com/5839686/29504628-d59ec818-85f5-11e7-86d0-137eac0a0195.png" width="100"> |
| Notification | <img src="https://user-images.githubusercontent.com/5839686/29504630-d5a960ac-85f5-11e7-8099-8ed8f4b68a83.png" width="100"> <img src="https://user-images.githubusercontent.com/5839686/29504631-d5adafea-85f5-11e7-84d2-e88e4c293743.png" width="100"> |

## Credits ##

This course project has been influenced from https://play.google.com/store/apps/details?id=com.joshclemm.android.quake 

It is not a replica or replacement of the above-mentioned android app but it is just an attempt by a student to learn android programming.


## DISCLAIMER ##

This is just a course project and source of data is the free APIs https://earthquake.usgs.gov/.

Please do not use this for reference for earthquake alert or analysis, instead directly go to https://earthquake.usgs.gov/
