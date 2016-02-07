package design.semicolon.instagram.adapters;

import design.semicolon.instagram.R;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import design.semicolon.instagram.activity.VideoPlayerActivity;
import design.semicolon.instagram.models.Post;
import design.semicolon.instagram.views.FeedItemViewHolder;

/**
 * Created by dsaha on 2/3/16.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedItemViewHolder>  {

    ArrayList<Post> mPosts;
    Context mContext;

    public FeedAdapter(ArrayList<Post> posts, Context context) {
        this.mPosts = posts;
        this.mContext = context;
    }

    public FeedAdapter(Context context) {
        this.mContext = context;
    }

    public void addPosts(ArrayList<Post> posts) {

        if (this.mPosts == null) {
            this.mPosts = new ArrayList<Post>();
        }

        this.mPosts.addAll(posts);
    }

    @Override
    public FeedItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_instagram_feed, parent, false);
        return new FeedItemViewHolder(view, this.mContext);
    }

    @Override
    public void onBindViewHolder(final FeedItemViewHolder feedItemViewHolder, int position) {

        Post post = null;

        if (position < getItemCount()) {
            post = mPosts.get(position);
        }

        if (post != null) {
            feedItemViewHolder.configureViewWithFeedMedium(post);
            final Post p = post;

            if (post.hasVideo()) {
                feedItemViewHolder.setOnImageClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent videoPlayerActivityIntent = new Intent(mContext, VideoPlayerActivity.class);
                        videoPlayerActivityIntent.putExtra(VideoPlayerActivity.PLAY_VIDEO_OF_POST, p);
                        mContext.startActivity(videoPlayerActivityIntent);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        if ( mPosts != null) {
            return mPosts.size();
        }
        return 0;
    }
}
