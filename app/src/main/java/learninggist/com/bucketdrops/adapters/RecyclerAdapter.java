package learninggist.com.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import learninggist.com.bucketdrops.R;

/**
 * Created by Deepak on 5/21/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private List<String> mItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    public RecyclerAdapter(Context context) {
        mItems = generateTempValues();
        mLayoutInflater = LayoutInflater.from(context);
    }

    private List<String> generateTempValues() {
        for (int i = 0; i < 100; i++) {
            String item = "item" + i;
            mItems.add(item);
        }
        return mItems;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        RecyclerHolder holder = new RecyclerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.bindData(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
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
