package edu.neu.madcourse.numad22su_jingming;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * This is a Parcelable Link class. This class represents the Link with the name and the url. The
 * instances created from this class can be written to and restored from the parcel.
 */
public class Link extends ArrayList<Parcelable> implements Parcelable {
    private final String name;
    private final String url;

    /**
     * Constructs a {@code Link} object with the specified name and URL.
     *
     * @param name - name of the link.
     * @param url -  url of the link.
     */
    public Link(String name, String url){
        this.name = name;
        this.url = url;
    }

    protected Link(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Link> CREATOR = new Creator<Link>() {
        @Override
        public Link createFromParcel(Parcel in) {
            return new Link(in);
        }

        @Override
        public Link[] newArray(int size) {
            return new Link[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + this.name + " URL: " + this.url;
    }
}
