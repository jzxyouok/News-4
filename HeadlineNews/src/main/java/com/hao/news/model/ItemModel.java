// (c)2016 Flipboard Inc, All Rights Reserved.

package com.hao.news.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemModel implements Parcelable{
    public String description;
    public String imageUrl;

    protected ItemModel(Parcel in) {
        description = in.readString();
        imageUrl = in.readString();
    }

    public ItemModel() {

    }

    public static final Creator<ItemModel> CREATOR = new Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel in) {
            return new ItemModel(in);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeString(imageUrl);
    }
}
