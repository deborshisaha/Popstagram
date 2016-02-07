package design.semicolon.instagram.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import design.semicolon.instagram.R;
import design.semicolon.instagram.dao.CommentDao;
import design.semicolon.instagram.helpers.Decorator;
import design.semicolon.instagram.models.Comment;
import design.semicolon.instagram.models.Post;

public class CommentItemViewHolder extends RecyclerView.ViewHolder {

    private RoundedImageView mProfilePictureImageView;
    private TextView mCommentTextView;
    private TextView mTimeStampTextView;
    private Context context;
    private ImageView loadMoreImageButton;
    private View seperatorLine;
    private View.OnClickListener ocl;

    public CommentItemViewHolder(View view, Context context, View.OnClickListener ocl) {
        super(view);
        this.context = context;

        this.mProfilePictureImageView = (RoundedImageView)view.findViewById(R.id.pp_comment_image);
        this.mCommentTextView = (TextView)view.findViewById(R.id.comment_text);
        this.mTimeStampTextView = (TextView)view.findViewById(R.id.comment_timestamp_text);
        this.loadMoreImageButton = (ImageView) view.findViewById(R.id.load_more);
        this.seperatorLine = (View) view.findViewById(R.id.seperator);

        this.ocl = ocl;
    }

    public void configureViewWithFeedMedium(Post medium) {

        if (medium != null &&
                medium.getCreatedBy()!= null &&
                medium.getCreatedBy().getProfilePictureURL() != null &&
                medium.getCreatedBy().getProfilePictureURL().length() != 0 &&
                this.mProfilePictureImageView != null) {
            Picasso.with(this.context).load(medium.getCreatedBy().getProfilePictureURL()).placeholder(R.drawable.ph_pp).into(this.mProfilePictureImageView);
        } else {
            this.mProfilePictureImageView.setImageResource(R.drawable.ph_pp);
        }

        // Time of Post
        this.mTimeStampTextView.setText(medium.timePostedInRelativeTime());

        // Comment with HTML
        String HTMLifiedUsernameString = Decorator.getDecorizedUsername(this.context, medium.getCreatedBy()) ;
        String HTMLifiedPhotoDescriptionString = Decorator.getDecorizedText(this.context, medium.getPhotoDescription());
        String string = HTMLifiedUsernameString+" "+HTMLifiedPhotoDescriptionString;

        if (string != null) {
            this.mCommentTextView.setText(Html.fromHtml(string), TextView.BufferType.NORMAL);
        }

        this.mCommentTextView.setVisibility(View.VISIBLE);
        this.seperatorLine.setVisibility(View.VISIBLE);

        this.loadMoreImageButton.setVisibility(View.GONE);
        this.loadMoreImageButton.setOnClickListener(null);
    }

    public void configureViewToLoadMore () {

        this.mProfilePictureImageView.setVisibility(View.GONE);
        this.mCommentTextView.setVisibility(View.GONE);
        this.mTimeStampTextView.setVisibility(View.GONE);
        this.seperatorLine.setVisibility(View.GONE);

        this.loadMoreImageButton.setVisibility(View.VISIBLE);
        this.loadMoreImageButton.setOnClickListener(this.ocl);
    }

    public void configureViewWithComment(Comment comment) {

        if (comment != null &&
                comment.getUser()!= null &&
                comment.getUser().getProfilePictureURL() != null &&
                comment.getUser().getProfilePictureURL().length() != 0 &&
                this.mProfilePictureImageView != null) {
            Picasso.with(this.context).load(comment.getUser().getProfilePictureURL()).placeholder(R.drawable.ph_pp).into(this.mProfilePictureImageView);
        } else {
            this.mProfilePictureImageView.setImageResource(R.drawable.ph_pp);
        }

        // Time of Post
        this.mTimeStampTextView.setText(comment.timePostedInRelativeTime());

        // Comment with HTML
        configureTextViewWithComment(this.mCommentTextView, comment);

        this.loadMoreImageButton.setVisibility(View.GONE);
        this.seperatorLine.setVisibility(View.GONE);
        this.loadMoreImageButton.setOnClickListener(null);
    }

    private void configureTextViewWithComment (TextView textView, Comment comment) {

        if (comment!=null) {
            String HTMLifiedUsernameString = Decorator.getDecorizedUsername(this.context, comment.getUser()) ;
            String HTMLifiedCommentString = Decorator.getDecorizedText(this.context, comment.getCommentText());
            String string = HTMLifiedUsernameString+" "+HTMLifiedCommentString;

            textView.setText(Html.fromHtml(string), TextView.BufferType.NORMAL);
            textView.setVisibility(View.VISIBLE);
        }
    }

}
