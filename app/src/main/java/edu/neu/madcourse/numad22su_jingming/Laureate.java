package edu.neu.madcourse.numad22su_jingming;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Laureate extends ArrayList<Parcelable>{
    private final String id;
    private final String fullName;
    private final String prize;
    private final String year;

    /**
     * Constructs a {@code Laureate} object with the specified name and URL.
     *
     * @param id - numeric ID of the Laureate (unique key for each Nobel Laureate)
     * @param fullName - full name of the Laureate in "givenName familyName"
     * @param prize - nobel prize of the Laureate
     * @param year - year of the Laureate wins the nobel prize
     */
    public Laureate(String id, String fullName, String prize, String year){
        this.id = id;
        this.fullName = fullName;
        this.prize = prize;
        this.year = year;
    }

    protected Laureate(Parcel in) {
        id = in.readString();
        fullName = in.readString();
        prize = in.readString();
        year = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPrize() {
        return prize;
    }

    public String getYear() {
        return year;
    }

    @NonNull
    @Override
    public String toString() {
        return "ID: " + getId() + " Name: " + getFullName() + " Prize: " + getPrize() + " Year: "
                + getYear();
    }
}