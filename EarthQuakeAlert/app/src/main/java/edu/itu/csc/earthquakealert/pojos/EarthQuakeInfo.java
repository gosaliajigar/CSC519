package edu.itu.csc.earthquakealert.pojos;

import android.util.Log;

import edu.itu.csc.earthquakealert.activities.MainActivity;
import edu.itu.csc.earthquakealert.utils.Utils;

/**
 * EarthQuakeInfo pojo to hold earth quake related information.
 *
 * @author "Jigar Gosalia"
 *
 */
public class EarthQuakeInfo {

    private String magnitude;

    private double longitude;

    private double latitude;

    private String place;

    private double depth;

    private String time;

    private String url;

    private String significance;

    private String eventId;

    private String status;

    private String title;

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public String getFormattedPlace() { return Utils.getPlace(place); }

    public double getDepth() {
        return depth;
    }

    public String getTime() {
        return time;
    }

    public String getFormattedTime() {
        String formattedTime = "N/A";
        try {
            formattedTime =  Utils.getDateTime(Long.parseLong(time));
        } catch (Exception exception) {
            Log.d(MainActivity.APP_TAG, "EarthQuakeInfo getFormattedTime: " + exception.toString());
        }
        return formattedTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getSignificance() { return significance; }

    public String getEventId() { return eventId; }

    public String getFormattedCoordinates() {return this.getLatitude() + "\u00B0, " + this.getLongitude() + "\u00B0"; }

    public EarthQuakeInfo(String magnitude, double longitude, double latitude, String place, double depth, String time, String url, String status, String title, String significance, String eventId) {
        this.magnitude = magnitude;
        this.longitude = longitude;
        this.latitude = latitude;
        this.place = place;
        this.depth = depth;
        this.time = time;
        this.url = url;
        this.status = status;
        this.title = title;
        this.significance = significance;
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "EarthQuakeInfo{" +
                "magnitude='" + magnitude + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", place='" + place + '\'' +
                ", depth='" + depth + '\'' +
                ", time='" + time + '\'' +
                ", url='" + url + '\'' +
                ", significance='" + significance + '\'' +
                ", eventId='" + eventId + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
