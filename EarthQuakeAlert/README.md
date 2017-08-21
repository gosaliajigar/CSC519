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
| Launcher | ![000-launcher](https://user-images.githubusercontent.com/5839686/29504612-d55ee77a-85f5-11e7-9c2e-e77d712ac2a3.png) |
| Navigation Drawer | ![000-navigationdrawer](https://user-images.githubusercontent.com/5839686/29504617-d5702e04-85f5-11e7-9c28-03b78ece8bf7.png) |
| MainActivity | ![010-loadinglatestearthquakes](https://user-images.githubusercontent.com/5839686/29504615-d56b5cbc-85f5-11e7-85aa-9b73614fc25d.png) ![011-latestearthquakes](https://user-images.githubusercontent.com/5839686/29504613-d56b01ea-85f5-11e7-9cdc-b61cbbd25061.png) ![012-earthquakedetails](https://user-images.githubusercontent.com/5839686/29504614-d56b3d22-85f5-11e7-8909-bddd38ceee5b.png) ![012-linktousgs](https://user-images.githubusercontent.com/5839686/29504616-d56cc7f0-85f5-11e7-9dc0-fd74c5007862.png) ![013-nodata](https://user-images.githubusercontent.com/5839686/29504618-d5745132-85f5-11e7-8973-245574f60a5b.png) ![014-interneterror](https://user-images.githubusercontent.com/5839686/29504620-d580237c-85f5-11e7-8c5e-333de9c7f957.png) |
| MapActivity | ![020-loadingviewearthquakes](https://user-images.githubusercontent.com/5839686/29504619-d57fe01a-85f5-11e7-8204-691a0b13b1c6.png) ![021-viewearthquakes](https://user-images.githubusercontent.com/5839686/29504621-d5822f5a-85f5-11e7-85e7-d7c29ecf3060.png) ![021-viewearthquakesdetails](https://user-images.githubusercontent.com/5839686/29504625-d5957240-85f5-11e7-9319-d06850010866.png) ![022-nodata](https://user-images.githubusercontent.com/5839686/29504622-d584e902-85f5-11e7-9d3b-c9308081113a.png) ![023-interneterror](https://user-images.githubusercontent.com/5839686/29504623-d5887806-85f5-11e7-9c5d-b63157ada203.png) |
| StatisticsActivity | ![030-loadingearthquakestatistics](https://user-images.githubusercontent.com/5839686/29504624-d5948f1a-85f5-11e7-9134-22ddc9f7a1e3.png) ![031-earthquakestatistics](https://user-images.githubusercontent.com/5839686/29504626-d596de64-85f5-11e7-988a-93b9d97fed32.png) ![033-interneterror](https://user-images.githubusercontent.com/5839686/29504629-d5a95e40-85f5-11e7-8bf0-7970056db102.png) |
| DetailsActivity |  |
| SettingsActivity | ![050-settings](https://user-images.githubusercontent.com/5839686/29504627-d59a58fa-85f5-11e7-800a-73755679bb11.png) |
| AboutActivity | ![060-about](https://user-images.githubusercontent.com/5839686/29504628-d59ec818-85f5-11e7-86d0-137eac0a0195.png) |
| Notification | <img width="720" alt="070-notificationbar" src="https://user-images.githubusercontent.com/5839686/29504630-d5a960ac-85f5-11e7-8099-8ed8f4b68a83.png"> <img width="720" alt="071-notificationdisplay" src="https://user-images.githubusercontent.com/5839686/29504631-d5adafea-85f5-11e7-84d2-e88e4c293743.png"> |


## Credits ##

This course project has been influenced from https://play.google.com/store/apps/details?id=com.joshclemm.android.quake 

It is not a replica or replacement of the above-mentioned android app but it is just an attempt by a student to learn android programming.


## DISCLAIMER ##

This is just a course project and source of data is the free APIs https://earthquake.usgs.gov/.

Please do not use this for reference for earthquake alert or analysis, instead directly go to https://earthquake.usgs.gov/
