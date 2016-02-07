package design.semicolon.instagram.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import design.semicolon.instagram.R;
import design.semicolon.instagram.adapters.FeedAdapter;
import design.semicolon.instagram.dao.PostDao;
import design.semicolon.instagram.dao.PostDaoImpl;

public class FeedActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeContainer;

    private FeedAdapter mFeedAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private PostDaoImpl mPostDaoImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        mRecyclerView = (RecyclerView) findViewById(R.id.feed);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Set the layout manager
        mRecyclerViewLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        // Initialize the adapter
        mFeedAdapter = new FeedAdapter( FeedActivity.this);

        // Set the adapter
        mRecyclerView.setAdapter(mFeedAdapter);

        mPostDaoImpl = new PostDaoImpl(mFeedAdapter);

        mPostDaoImpl.loadPosts(null);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Load posts
                mPostDaoImpl.loadPosts(new PostDao.OnFeedUpdateListener() {
                    @Override
                    public void onFeedUpdate() {
                        swipeContainer.setRefreshing(false);
                    }
                });
            }
        });

    }
}
