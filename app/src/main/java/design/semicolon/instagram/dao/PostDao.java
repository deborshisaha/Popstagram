package design.semicolon.instagram.dao;

import java.util.ArrayList;

import design.semicolon.instagram.models.Post;

/**
 * Created by dsaha on 2/7/16.
 */
public interface PostDao {

    public void loadPosts(OnFeedUpdateListener onFeedUpdateListener);

    public interface OnFeedUpdateListener {
        public void onFeedUpdate();
    }
}
