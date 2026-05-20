package com.example.a2_trwale_przechowywanie_danych;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Phone {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String manufacturer;

    @NonNull
    private String model;

    private int androidVersion;

    @NonNull
    private String website;

    public Phone(@NonNull String manufacturer, @NonNull String model, int androidVersion,
                 @NonNull String website) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.website = website;
    }

    @Ignore
    public Phone(long id, @NonNull String manufacturer, @NonNull String model, int androidVersion,
                 @NonNull String website) {
        this(manufacturer, model, androidVersion, website);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(@NonNull String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    public int getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(int androidVersion) {
        this.androidVersion = androidVersion;
    }

    @NonNull
    public String getWebsite() {
        return website;
    }

    public void setWebsite(@NonNull String website) {
        this.website = website;
    }
}