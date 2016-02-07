package design.semicolon.instagram.dao;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import design.semicolon.instagram.adapters.CommentsAdapter;
import design.semicolon.instagram.models.Comment;
import design.semicolon.instagram.models.Post;
import design.semicolon.instagram.restclient.InstagramRESTClient;

/**
 * Created by dsaha on 2/7/16.
 */
public class CommentDaoImpl {

    private final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";

    public CommentDaoImpl(CommentsAdapter commentsAdapter) {
        this.commentsAdapter = commentsAdapter;
    }

    private CommentsAdapter commentsAdapter;

    public void loadComments( Post post,CommentDao.OnCommentsLoadedListener ocl) {

        RequestParams params = new RequestParams();
        params.add("client_id", CLIENT_ID);

        InstagramRESTClient.get( post.getId()+"/comments", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                ArrayList<Comment> comments = new ArrayList<Comment>();

                JSONArray commentsJSONArray = null;
                try {
                    commentsJSONArray = response.getJSONArray("data");

                    for (int i = 0; i < commentsJSONArray.length(); i++) {

                        JSONObject commentJSONObject = commentsJSONArray.getJSONObject(i);
                        Comment comment = new Comment(commentJSONObject);
                        comments.add(comment);
                    }

                    commentsAdapter.addComments(comments);
                    commentsAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
