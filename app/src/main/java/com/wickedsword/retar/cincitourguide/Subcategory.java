package com.wickedsword.retar.cincitourguide;

public class Subcategory {
    // member variables
    private String mParentCategory;
    private String mCategoryName;
    private int mCategoryImageResourceIde;
    private int mCategoryId;

    // constructor with image
    public Subcategory(String parentCategory, String categoryName, int categoryId, int imageResourceId) {
       mParentCategory = parentCategory;
        mCategoryName = categoryName;
        mCategoryId = categoryId;
        mCategoryImageResourceIde = imageResourceId;
    }

    // get the category name
    public String getParentCategoryName() {
        return mParentCategory;
    }

    // get the category name
    public String getCategoryName() {
        return mCategoryName;
    }

    // get the category id
    public int getCategoryId() {
        return mCategoryId;
    }

    // get the background image id
    public int getCategoryImage() {
        return mCategoryImageResourceIde;
    }
}
