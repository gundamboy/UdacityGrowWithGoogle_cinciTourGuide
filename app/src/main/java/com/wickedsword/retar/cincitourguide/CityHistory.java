package com.wickedsword.retar.cincitourguide;


public class CityHistory {
    private String mSectionTitle;
    private String mSectionText;

    /**
     * Constructor
     * @param mSectionTitle
     * @param mSectionText
     */
    public CityHistory(String mSectionTitle, String mSectionText) {
        this.mSectionTitle = mSectionTitle;
        this.mSectionText = mSectionText;
    }

    /**
     * Returns the section title
     * @return
     */
    public String getmSectionTitle() {
        return mSectionTitle;
    }

    /**
     * Returns the section text
     * @return
     */
    public String getmSectionText() {
        return mSectionText;
    }
}
