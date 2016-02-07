package design.semicolon.instagram.models;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by dsaha on 2/6/16.
 */
public class Placemark implements Serializable {

    public Placemark(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    private String name;
    private Double latitude;
    private Double longitude;
}
