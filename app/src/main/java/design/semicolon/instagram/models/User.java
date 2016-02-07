package design.semicolon.instagram.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by dsaha on 2/6/16.
 */
public class User implements Serializable {

    public User(String name, String profilePictureURL, String id, String fullName) {
        this.username = name;
        this.profilePictureURL = profilePictureURL;
        this.id = id;
        this.fullName = fullName;
    }

    public User(JSONObject userJSONObject) throws JSONException {

        try {

            // Find username
            if (userJSONObject.getString("username") != null) {
                this.username = userJSONObject.getString("username");
            }

            if (userJSONObject.getString("profile_picture") != null) {
                this.profilePictureURL = userJSONObject.getString("profile_picture");
            }

            if (userJSONObject.getString("id") != null) {
                String id = userJSONObject.getString("id");
                this.id = id;
            }

            if (userJSONObject.getString("full_name") != null) {
                String fullName = userJSONObject.getString("full_name");
                this.fullName = fullName;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    private String username;
    private String profilePictureURL;
    private String id;
    private String fullName;
}
