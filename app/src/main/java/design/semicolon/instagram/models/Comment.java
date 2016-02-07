package design.semicolon.instagram.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;

import design.semicolon.instagram.helpers.DateHelper;

/**
 * Created by dsaha on 2/6/16.
 */
public class Comment implements Comparable<Comment>, Serializable {

    public String getCommentText() {
        return commentText;
    }

    public User getUser() {
        return user;
    }

    public String timeCommentedInRelativeTime() {
        return "Relative time";
    }

    public String getId() {
        return id;
    }

    public Comment(String commentText, User user, Timestamp createdTime, String id) {
        this.commentText = commentText;
        this.user = user;
        this.createdTime = createdTime;
        this.id = id;
    }

    public Comment(JSONObject commentJSONObject) throws JSONException {

        try {

            // Find Location
            if (commentJSONObject.getString("created_time") != null) {
                String createdTime = commentJSONObject.getString("created_time");
                this.createdTime = new Timestamp(Long.parseLong(createdTime));
            }

            if (commentJSONObject.getString("text") != null) {
                String text = commentJSONObject.getString("text");
                this.commentText = text;
            }

            if (commentJSONObject.getJSONObject("from") != null) {
                JSONObject userJSONObject = commentJSONObject.getJSONObject("from");
                this.user = new User(userJSONObject);
            }

            if (commentJSONObject.getString("id") != null) {
                String id = commentJSONObject.getString("id");
                this.id = id;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public String timePostedInRelativeTime () {
        return DateHelper.getInstagramStyleRelativeTiming(this.createdTime.getTime());
    }

    public int compareTo(Comment comment)
    {
        return (createdTime.compareTo(comment.getCreatedTime()));
    }

    private String commentText;
    private User user;
    private Timestamp createdTime;
    private String id;
}
