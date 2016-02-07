package design.semicolon.instagram.dao;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import design.semicolon.instagram.adapters.FeedAdapter;
import design.semicolon.instagram.models.Post;
import design.semicolon.instagram.restclient.InstagramRESTClient;

/**
 * Created by dsaha on 2/7/16.
 */
public class PostDaoImpl implements PostDao {

    private final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private FeedAdapter feedAdapter;
    private OnFeedUpdateListener onFeedUpdateListener;

    public PostDaoImpl(FeedAdapter adapter) {
        this.feedAdapter = adapter;
    }

    public void loadPosts(OnFeedUpdateListener ofl) {

        this.onFeedUpdateListener = ofl;

        RequestParams params = new RequestParams();
        params.add("client_id", CLIENT_ID);

        InstagramRESTClient.get("popular", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                ArrayList<Post> feedPosts = new ArrayList<Post>();

                JSONArray feedPostsJSONArray = null;
                try {
                    feedPostsJSONArray = response.getJSONArray("data");

                    for (int i = 0; i < feedPostsJSONArray.length(); i++) {

                        JSONObject postJSONObject = feedPostsJSONArray.getJSONObject(i);
                        Post post = new Post(postJSONObject);
                        feedPosts.add(post);
                    }

                    feedAdapter.addPosts(feedPosts);
                    feedAdapter.notifyDataSetChanged();

                    if (onFeedUpdateListener != null) {
                        onFeedUpdateListener.onFeedUpdate();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}