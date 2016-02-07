package design.semicolon.instagram.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import design.semicolon.instagram.R;
import design.semicolon.instagram.activity.CommentsActivity;
import design.semicolon.instagram.adapters.CommentsAdapter;
import design.semicolon.instagram.helpers.Decorator;
import design.semicolon.instagram.models.Comment;
import design.semicolon.instagram.models.Post;

public class FeedItemViewHolder extends RecyclerView.ViewHolder {

    private RoundedImageView mProfilePictureImageView;
    private TextView mUsernameOfSubmitterTextView;
    private Context context;

    private ImageView mPhotoView;
    private ImageView mPlayVideoImageView;
    private TextView mPhotoDecriptionTextView;

    private ImageView mNumberOfLikesIconImageView;
    private TextView mNumberOfLikesTextView;

    private TextView mNumberOfCommentsTextView;
    private TextView mTimeOfPostTextView;
    private TextView mLocationNameTextView;

    private TextView mCommentOneTextView;
    private TextView mCommentTwoTextView;

    private View.OnClickListener onImageClickListener;

    public FeedItemViewHolder(View view, Context context) {
        super(view);
        this.context = context;

        this.mProfilePictureImageView = (RoundedImageView)view.findViewById(R.id.profile_picture_image);
        this.mUsernameOfSubmitterTextView = (TextView)view.findViewById(R.id.submitter_username_text);

        this.mPlayVideoImageView = (ImageView)view.findViewById(R.id.play_video);
        this.mPhotoView = (ImageView)view.findViewById(R.id.medium);
        this.mPhotoDecriptionTextView = (TextView)view.findViewById(R.id.photo_description);

        this.mTimeOfPostTextView = (TextView)view.findViewById(R.id.time_of_post_text);
        this.mLocationNameTextView = (TextView)view.findViewById(R.id.location_name_text);

        this.mNumberOfLikesIconImageView = (ImageView)view.findViewById(R.id.like_counts_icon);
        this.mNumberOfLikesTextView = (TextView)view.findViewById(R.id.like_counts_text);

        this.mNumberOfCommentsTextView = (TextView)view.findViewById(R.id.comments_count_text);

        this.mCommentOneTextView = (TextView)view.findViewById(R.id.comment1__text);
        this.mCommentTwoTextView = (TextView)view.findViewById(R.id.comment2__text);
    }

    @Override
    public String toString() {
        return "";
    }

    public void configureViewWithFeedMedium(final Post post) {

        if (post != null &&
                post.getCreatedBy()!= null &&
                post.getCreatedBy().getProfilePictureURL() != null &&
                post.getCreatedBy().getProfilePictureURL().length() != 0 &&
                this.mProfilePictureImageView != null) {
            Picasso.with(this.context).load(post.getCreatedBy().getProfilePictureURL()).placeholder(R.drawable.ph_pp).into(this.mProfilePictureImageView);
        }

        // Loading photo
        Picasso.with(this.context).load(post.getMediumURL()).placeholder(R.drawable.ph).into(this.mPhotoView);
        String HTMLifiedUsernameString = Decorator.getDecorizedUsername(this.context, post.getCreatedBy()) ;
        String HTMLifiedString = Decorator.getDecorizedText(this.context, post.getPhotoDescription());

        String string = null;

        if (HTMLifiedUsernameString != null && HTMLifiedString != null) {
            string = HTMLifiedUsernameString+" "+HTMLifiedString;
        }

        if (string != null) {
            this.mPhotoDecriptionTextView.setVisibility(View.VISIBLE);
            this.mPhotoDecriptionTextView.setText(Html.fromHtml(string), TextView.BufferType.NORMAL);
        } else {
            this.mPhotoDecriptionTextView.setVisibility(View.GONE);
            this.mPhotoDecriptionTextView.setText("");
        }

        if (post.locationName() == null || post.locationName().length() == 0) {
            this.mLocationNameTextView.setVisibility(View.GONE);
            this.mLocationNameTextView.setText("");

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.mUsernameOfSubmitterTextView.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            this.mUsernameOfSubmitterTextView.setLayoutParams(params);
        } else {
            this.mLocationNameTextView.setText(post.locationName());
            this.mLocationNameTextView.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.mUsernameOfSubmitterTextView.getLayoutParams();
            params.removeRule(RelativeLayout.CENTER_VERTICAL);
            this.mUsernameOfSubmitterTextView.setLayoutParams(params);
        }

        // User name
        if (HTMLifiedUsernameString != null) {
            this.mUsernameOfSubmitterTextView.setText(Html.fromHtml(HTMLifiedUsernameString), TextView.BufferType.SPANNABLE);
        }

        // Time of Post
        this.mTimeOfPostTextView.setText(post.timePostedInRelativeTime());

        // Number of likes
        if (post.getNumberOfLikes() == 0 ) {
            this.mNumberOfLikesTextView.setVisibility(View.GONE);
            this.mNumberOfLikesIconImageView.setVisibility(View.GONE);
        } else {
            this.mNumberOfLikesTextView.setVisibility(View.VISIBLE);
            this.mNumberOfLikesIconImageView.setVisibility(View.VISIBLE);
            this.mNumberOfLikesTextView.setText( NumberFormat.getInstance().format(post.getNumberOfLikes()) + " likes");
        }

        // Number of comments
        if (post.getNumberOfComments() == 0 || post.getNumberOfComments() == 2) {
            this.mNumberOfCommentsTextView.setText("");
            this.mNumberOfCommentsTextView.setVisibility(View.GONE);
        } else {
            this.mNumberOfCommentsTextView.setText("View all "+ post.getNumberOfComments()+ " comments");
            this.mNumberOfCommentsTextView.setVisibility(View.VISIBLE);
        }

        // Last two comments
        configureTextViewWithComment(this.mCommentOneTextView, post.getNthMostRecentComment(1));
        configureTextViewWithComment(this.mCommentTwoTextView, post.getNthMostRecentComment(0));

        if (post.hasVideo()) {
            this.mPlayVideoImageView.setVisibility(View.VISIBLE);
        } else {
            this.mPlayVideoImageView.setVisibility(View.GONE);
        }

        // Set onClickListener
        this.mNumberOfCommentsTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent commentActivityIntent = new Intent(context, CommentsActivity.class);
                commentActivityIntent.putExtra(CommentsAdapter.POST_CLICKED, post);
                context.startActivity(commentActivityIntent);
            }
        });
    }

    public void setOnImageClickListener(View.OnClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;

        if (this.onImageClickListener != null) {
            this.mPhotoView.setOnClickListener(this.onImageClickListener);
        }
    }

    private void configureTextViewWithComment (TextView textView, Comment comment) {

        if (comment!=null) {
            String HTMLifiedUsernameString = Decorator.getDecorizedUsername(this.context, comment.getUser()) ;
            String HTMLifiedCommentString = Decorator.getDecorizedText(this.context, comment.getCommentText());
            String string = HTMLifiedUsernameString+" "+HTMLifiedCommentString;

            textView.setText(Html.fromHtml(string), TextView.BufferType.NORMAL);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setText("");
            textView.setVisibility(View.GONE);
        }
    }
}