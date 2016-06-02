package learninggist.com.bucketdrops.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import learninggist.com.bucketdrops.AddListener;
import learninggist.com.bucketdrops.DropListener;
import learninggist.com.bucketdrops.R;
import learninggist.com.bucketdrops.SwipeListener;
import learninggist.com.bucketdrops.beans.Drop;
import learninggist.com.bucketdrops.extras.Util;

/**
 * Created by Deepak on 5/21/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {

    private DropListener mDropListener;
    private RealmResults<Drop> mItems;
    private LayoutInflater mLayoutInflater;
    public static final int FOOTER = 1;
    public static final int ITEM = 0;
    private AddListener mAddListener;
    private Realm mRealm;

    public RecyclerAdapter(Context context, Realm realm, RealmResults<Drop> results) {
        mLayoutInflater = LayoutInflater.from(context);
        update(results);
        mRealm = realm;
    }

    public RecyclerAdapter(Context context, Realm realm, RealmResults<Drop> results, AddListener listener, DropListener dropListener) {
        mLayoutInflater = LayoutInflater.from(context);
        update(results);
        mRealm = realm;
        mAddListener = listener;
        mDropListener = dropListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = mLayoutInflater.inflate(R.layout.list_item, parent, false);
            DropHolder holder = new DropHolder(view);
            return holder;
        } else {
            View view = mLayoutInflater.inflate(R.layout.add_drop_footer, parent, false);
            FooterHolder holder = new FooterHolder(view);
            return holder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mItems == null || position < mItems.size()) {
            return ITEM;
        } else {
            return FOOTER;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropHolder) {
            if (mItems.get(position).isValid()) {
                DropHolder dropHolder = (DropHolder) holder;
                Drop drop = mItems.get(position);
                dropHolder.bindData(drop.getWhatJob());
                dropHolder.setBackground(drop.isCompleted());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mItems == null || mItems.isEmpty())
            return 0;
        else
            return mItems.size() + 1;
    }

    public void update(RealmResults<Drop> results) {
        mItems = results;
        notifyDataSetChanged();
    }

    @Override
    public void onSwipe(int adapterPosition) {
        if (adapterPosition < mItems.size()) {
            mRealm.beginTransaction();
            mItems.get(adapterPosition).deleteFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(adapterPosition);
        }
    }

    public void setCompleted(int position) {
        if (position < mItems.size()) {
            mRealm.beginTransaction();
            mItems.get(position).setCompleted(true);
            mRealm.commitTransaction();
            notifyItemChanged(position);
        }
    }


    public class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mDropInfo;
        private TextView mDropDate;
        private View mItemView;
        private Context mContext;

        public DropHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mItemView = itemView;
            mDropInfo = (TextView) itemView.findViewById(R.id.tv_dropInfo);
            mDropDate = (TextView) itemView.findViewById(R.id.tv_dropDate);
            itemView.setOnClickListener(this);
        }

        public void bindData(String s) {
            mDropInfo.setText(s);
        }

        @Override
        public void onClick(View v) {
            mDropListener.MarkCompleted(getAdapterPosition());
        }

        public void setBackground(boolean completed) {
            Drawable drawable;
            if (completed) {
                drawable = ContextCompat.getDrawable(mContext, R.color.bg_drop_complete);
            } else {
                drawable = ContextCompat.getDrawable(mContext, R.drawable.list_item_background_selector);
            }
            Util.setBackground(mItemView, drawable);
        }
    }


    public class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button mBtnAdd;

        public FooterHolder(View itemView) {
            super(itemView);
            mBtnAdd = (Button) itemView.findViewById(R.id.footer_btn);
            mBtnAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mAddListener.add();
        }
    }
}
