package com.wickedsword.retar.cincitourguide;


public class CityLocation {
    // set up member variables
    private int mActivityId;
    private String mActivityName;
    private int mActivityImageId;
    private String mActivityTeaserText;
    private String mActivityDescription;
    private String mActivityWebsite;
    private String mActivityPhoneNumber;
    private String mActivityStreet;
    private String mActivityCityStateZip;
    private String mActivityDirectionsString;
    private String mActivityRates;
    private String mActivityHours;

    // constructor
    public CityLocation(int mActivityId, String mActivityName, int mActivityImageId, String mActivityTeaserText, String mActivityDescription, String mActivityWebsite, String mActivityPhoneNumber, String mActivityStreet, String mActivityCityStateZip, String mActivityDirectionsString, String mActivityRates, String mActivityHours) {
        this.mActivityId = mActivityId;
        this.mActivityName = mActivityName;
        this.mActivityImageId = mActivityImageId;
        this.mActivityTeaserText = mActivityTeaserText;
        this.mActivityDescription = mActivityDescription;
        this.mActivityWebsite = mActivityWebsite;
        this.mActivityPhoneNumber = mActivityPhoneNumber;
        this.mActivityStreet = mActivityStreet;
        this.mActivityCityStateZip = mActivityCityStateZip;
        this.mActivityDirectionsString = mActivityDirectionsString;
        this.mActivityRates = mActivityRates;
        this.mActivityHours = mActivityHours;
    }

    // getters
    public int getmActivityId() {
        return mActivityId;
    }

    public String getmActivityName() {
        return mActivityName;
    }

    public int getmActivityImageId() {
        return mActivityImageId;
    }

    public String getmActivityTeaserText() {
        return mActivityTeaserText;
    }

    public String getmActivityDescription() {
        return mActivityDescription;
    }

    public String getmActivityWebsite() {
        return mActivityWebsite;
    }

    public String getmActivityPhoneNumber() {
        return mActivityPhoneNumber;
    }

    public String getmActivityStreet() {
        return mActivityStreet;
    }

    public String getmActivityCityStateZip() {
        return mActivityCityStateZip;
    }

    public String getmActivityDirectionsString() {
        return mActivityDirectionsString;
    }

    public String getmActivityRates() {
        return mActivityRates;
    }

    public String getmActivityHours() {
        return mActivityHours;
    }
}
