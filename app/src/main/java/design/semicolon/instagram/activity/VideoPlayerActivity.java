package design.semicolon.instagram.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import design.semicolon.instagram.R;
import design.semicolon.instagram.adapters.CommentsAdapter;
import design.semicolon.instagram.models.Post;

/**
 * Created by dsaha on 2/7/16.
 */
public class VideoPlayerActivity extends AppCompatActivity {

    @Bind(R.id.video_view)
    VideoView mVideoView;

    private Post mPost;

    public static final String PLAY_VIDEO_OF_POST = "post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);

        setTitle(R.string.video_player);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mPost = (Post) getIntent().getSerializableExtra(PLAY_VIDEO_OF_POST);
        }

        if (mPost != null) {
            playVideo(mPost);
        }
    }


    private void playVideo(Post post) {
        try {
            MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(mVideoView);
            Uri video = Uri.parse(post.getVideoURLStandardResolution());
            mVideoView.setMediaController(mediacontroller);
            mVideoView.setVideoURI(video);

        } catch (Exception e) {
            e.printStackTrace();
        }

        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
            }
        });
    }
}
