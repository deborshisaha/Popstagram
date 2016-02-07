package design.semicolon.instagram.dao;

/**
 * Created by dsaha on 2/7/16.
 */
public interface CommentDao {

    public void loadComments(OnCommentsLoadedListener onCommentsLoadedListener);

    public interface OnCommentsLoadedListener {
        public void onCommentsLoaded();
    }

}
