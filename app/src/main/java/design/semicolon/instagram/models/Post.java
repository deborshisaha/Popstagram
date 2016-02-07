package design.semicolon.instagram.models;

import android.location.Location;
import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import design.semicolon.instagram.helpers.DateHelper;

/**
 * Created by dsaha on 2/3/16.
 */
public class Post implements Serializable {

    private String mMediumURL;
    private Placemark mPlacemark;
    private Integer mNumberOfLikes;
    private Integer mNumberOfComments;
    private String id;
    private String mVideoURLStandardResolution;

    private Timestamp mCreatedTime;
    private long mCreatedTimestamp;

    private String mPhotoDescription;

    // Store list of comments with most recent comment at '0'
    private ArrayList<Comment> comments;

    // Post created by
    private User mCreatedBy;

    public User getCreatedBy() {
        return mCreatedBy;
    }

    public String getMediumURL() {
        return mMediumURL;
    }

    public Integer getNumberOfLikes() {
        if (mNumberOfLikes == null) {
            return 0;
        }

        return mNumberOfLikes;
    }

    public Integer getNumberOfComments() {
        if (mNumberOfComments == null) {
            return 0;
        }

        return mNumberOfComments;
    }

    public String timePostedInRelativeTime () {
        return DateHelper.getInstagramStyleRelativeTiming(this.mCreatedTimestamp);
    }

    public String locationName () {

        if (this.mPlacemark != null) {
            return this.mPlacemark.getName();
        }

        return null;
    }

    public Comment getNthMostRecentComment(int nth) {

        if (this.comments == null || nth >= this.comments.size()) {
            return null;
        }

        return this.comments.get(nth);
    }

    public void addComments (ArrayList<Comment> appendComments) {
        comments.addAll(appendComments);
        Collections.sort(comments);
    }

    public String getPhotoDescription() {
        return mPhotoDescription;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public String getId() {
        return id;
    }

    public Post(JSONObject postAsJSONObject) throws JSONException {

        try {
            // Find Location
            if (postAsJSONObject.getString("id") != null) {
                this.id = postAsJSONObject.getString("id");
            }

            // Find Location
            if (postAsJSONObject.optJSONObject("location") != null) {
                Double latitude = postAsJSONObject.getJSONObject("location").getDouble("latitude");
                Double longitude = postAsJSONObject.getJSONObject("location").getDouble("longitude");
                String locationName = postAsJSONObject.getJSONObject("location").getString("name");
                this.mPlacemark = new Placemark(locationName, latitude, longitude);
            }

            if (postAsJSONObject.getJSONObject("caption") != null) {
                this.mPhotoDescription = postAsJSONObject.getJSONObject("caption").getString("text");
            } else {
                this.mPhotoDescription = "";
            }

            // Created by user
            if (postAsJSONObject.optJSONObject("caption")!= null &&
                    postAsJSONObject.optJSONObject("caption").optJSONObject("from") != null) {
                JSONObject userJSONObject = postAsJSONObject.getJSONObject("caption").getJSONObject("from");
                this.mCreatedBy = new User(userJSONObject);
            }

            // number of likes
            if (postAsJSONObject.optJSONObject("likes") != null) {
                int numberOfLikes = postAsJSONObject.getJSONObject("likes").getInt("count");
                this.mNumberOfLikes = numberOfLikes;
            }

            // number of comments
            if (postAsJSONObject.optJSONObject("comments") != null) {
                int numberOfComments = postAsJSONObject.getJSONObject("comments").getInt("count");
                this.mNumberOfComments = numberOfComments;
            }

            // images
            if (postAsJSONObject.optJSONObject("images") != null
                    && postAsJSONObject.optJSONObject("images").optJSONObject("standard_resolution") != null) {
                this.mMediumURL = postAsJSONObject.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            }

            if (postAsJSONObject.optJSONObject("videos") != null
                    && postAsJSONObject.optJSONObject("videos").optJSONObject("standard_resolution") != null) {
                this.mVideoURLStandardResolution = postAsJSONObject.getJSONObject("videos").getJSONObject("standard_resolution").getString("url");
            } else {
                this.mVideoURLStandardResolution = "";
            }

            // comments
            if (postAsJSONObject.optJSONObject("comments") != null &&
                    postAsJSONObject.optJSONObject("comments").optJSONArray("data") != null) {

                JSONArray commentsJSONArray = postAsJSONObject.getJSONObject("comments").getJSONArray("data");
                ArrayList<Comment> comments = new ArrayList<Comment>();

                for (int i = 0; i < commentsJSONArray.length(); i++) {

                    JSONObject commentJSONObject = commentsJSONArray.getJSONObject(i);
                    Comment comment = new Comment(commentJSONObject);
                    comments.add(comment);
                }

                this.comments = comments;

                Collections.sort(this.comments);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public boolean hasVideo () {
        return (this.mVideoURLStandardResolution != null && this.mVideoURLStandardResolution.length()!=0 );
    }

    public String getVideoURLStandardResolution() {
        return mVideoURLStandardResolution;
    }

}
