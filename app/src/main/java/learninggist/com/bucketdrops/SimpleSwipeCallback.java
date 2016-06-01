package learninggist.com.bucketdrops;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Deepak on 6/1/16.
 */
public class SimpleSwipeCallback extends ItemTouchHelper.Callback {
    SwipeListener mSwipeListener;

    public SimpleSwipeCallback(SwipeListener listener) {
        mSwipeListener = listener;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mSwipeListener.onSwipe(viewHolder.getAdapterPosition());
    }
}
