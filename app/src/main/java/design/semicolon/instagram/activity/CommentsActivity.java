package design.semicolon.instagram.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import design.semicolon.instagram.R;
import design.semicolon.instagram.adapters.CommentsAdapter;
import design.semicolon.instagram.dao.CommentDao;
import design.semicolon.instagram.dao.CommentDaoImpl;
import design.semicolon.instagram.models.Post;

/**
 * Created by dsaha on 2/6/16.
 */
public class CommentsActivity extends AppCompatActivity {

    @Bind(R.id.comment_feed) RecyclerView mRecyclerView;

    private CommentsAdapter mCommentsAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private Post mPost;

    private CommentDaoImpl commentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);

        // Set the layout manager
        mRecyclerViewLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mPost = (Post) getIntent().getSerializableExtra(CommentsAdapter.POST_CLICKED);
        }

        if (mPost != null) {
            // Initialize the adapter
            mCommentsAdapter = new CommentsAdapter(mPost.getComments(), CommentsActivity.this, mPost);

            // Set the adapter
            mRecyclerView.setAdapter(mCommentsAdapter);

            if (this.commentDao == null) {
                this.commentDao = new CommentDaoImpl(mCommentsAdapter);
            }

            mCommentsAdapter.setLoadMoreOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commentDao != null) {
                        commentDao.loadComments(mPost, null);
                    }
                }
            });
        }
    }
}
