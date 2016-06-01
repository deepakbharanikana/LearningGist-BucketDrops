package learninggist.com.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import learninggist.com.bucketdrops.AddListener;
import learninggist.com.bucketdrops.R;
import learninggist.com.bucketdrops.SwipeListener;
import learninggist.com.bucketdrops.beans.Drop;

/**
 * Created by Deepak on 5/21/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {

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

    public RecyclerAdapter(Context context, Realm realm, RealmResults<Drop> results, AddListener listener) {
        mLayoutInflater = LayoutInflater.from(context);
        update(results);
        mRealm = realm;
        mAddListener = listener;
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
            if(mItems.get(position).isValid()) {
                DropHolder dropHolder = (DropHolder) holder;
                Drop drop = mItems.get(position);
                dropHolder.bindData(drop.getWhatJob());
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

    public class DropHolder extends RecyclerView.ViewHolder {
        private TextView mDropInfo;
        private TextView mDropDate;

        public DropHolder(View itemView) {
            super(itemView);
            mDropInfo = (TextView) itemView.findViewById(R.id.tv_dropInfo);
            mDropDate = (TextView) itemView.findViewById(R.id.tv_dropDate);
        }

        public void bindData(String s) {
            mDropInfo.setText(s);
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
