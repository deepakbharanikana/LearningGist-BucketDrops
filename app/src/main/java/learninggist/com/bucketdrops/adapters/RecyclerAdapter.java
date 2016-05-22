package learninggist.com.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmResults;
import learninggist.com.bucketdrops.R;
import learninggist.com.bucketdrops.beans.Drop;

/**
 * Created by Deepak on 5/21/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private RealmResults<Drop> mItems;
    private LayoutInflater mLayoutInflater;

    public RecyclerAdapter(Context context, RealmResults<Drop> results) {
        mLayoutInflater = LayoutInflater.from(context);
        update(results);
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        RecyclerHolder holder = new RecyclerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        Drop drop = mItems.get(position);
        holder.bindData(drop.getWhatJob());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void update(RealmResults<Drop> results) {
        mItems = results;
        notifyDataSetChanged();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private TextView mDropInfo;
        private TextView mDropDate;

        public RecyclerHolder(View itemView) {
            super(itemView);
            mDropInfo = (TextView) itemView.findViewById(R.id.tv_dropInfo);
            mDropDate = (TextView) itemView.findViewById(R.id.tv_dropDate);
        }

        public void bindData(String s) {
            mDropInfo.setText(s);
        }
    }
}
