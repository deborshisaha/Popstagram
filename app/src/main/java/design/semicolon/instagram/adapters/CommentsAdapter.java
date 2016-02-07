package design.semicolon.instagram.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import design.semicolon.instagram.R;
import design.semicolon.instagram.dao.CommentDao;
import design.semicolon.instagram.models.Comment;
import design.semicolon.instagram.models.Post;
import design.semicolon.instagram.views.CommentItemViewHolder;

/**
 * Created by dsaha on 2/7/16.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentItemViewHolder> {

    public static final String POST_CLICKED = "PostClicked";

    public void setLoadMoreOnClickListener(View.OnClickListener loadMoreOnClickListener) {
        this.loadMoreOnClickListener = loadMoreOnClickListener;
    }

    private View.OnClickListener loadMoreOnClickListener;
    public CommentsAdapter(ArrayList<Comment> comments, Context context, Post medium) {
        this.mComments = comments;
        this.mContext = context;
        this.mPost = medium;
    }

    private ArrayList<Comment> mComments;
    private Context mContext;
    private Post mPost;
    private int offset;

    public void addComments(ArrayList<Comment> comments) {
        if (mComments ==null) {
            this.mComments = new ArrayList<Comment>();
        }

        mComments.removeAll(mComments);
        mComments.addAll(comments);
        Collections.sort(mComments);
    }

    @Override
    public CommentItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentItemViewHolder(view, this.mContext, this.loadMoreOnClickListener);
    }

    @Override
    public void onBindViewHolder(final CommentItemViewHolder commentItemViewHolder, int position) {

        Comment comment = null;

        if (position == 0) {
            // This is always the feed
            commentItemViewHolder.configureViewWithFeedMedium(this.mPost);
        } else if (position == 1 && (this.mPost.getComments().size()+1) < mPost.getNumberOfComments()) {
            // more to show
            commentItemViewHolder.configureViewToLoadMore();
        } else {
            int effectivePosition = position - this.offset;
            comment = mComments.get(effectivePosition);
            commentItemViewHolder.configureViewWithComment(comment);
        }
    }

    @Override
    public int getItemCount() {

        if (this.mPost.getComments().size() + 1 < mPost.getNumberOfComments()){
            this.offset = 2;
            // More to load
            return mComments.size() + this.offset;
        }

        this.offset = 1;
        // Title and comments to be loaded only
        return mComments.size() + this.offset ;
    }
}
