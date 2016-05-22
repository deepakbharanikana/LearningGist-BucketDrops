package learninggist.com.bucketdrops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import learninggist.com.bucketdrops.adapters.RecyclerAdapter;
import learninggist.com.bucketdrops.beans.Drop;


/**
 * Created by Deepak on 5/21/16.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ImageView mBackground;
    private Button mAddDropBtn;
    private RecyclerView mRecyclerView;
    private Realm mRealm;
    private RealmResults<Drop> mResults;
    private RecyclerAdapter mAdapter;

    private RealmChangeListener
            mRealmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            mAdapter.update(mResults);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setLayoutBackground();
        mRealm = Realm.getDefaultInstance();
        mResults = mRealm.where(Drop.class).findAllAsync();
        mAddDropBtn = (Button) findViewById(R.id.btn_addDrop);
        mAddDropBtn.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new RecyclerAdapter(this, mResults);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRealm.addChangeListener(mRealmChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRealm.removeChangeListener(mRealmChangeListener);
    }

    private void setLayoutBackground() {
        mBackground = (ImageView) findViewById(R.id.iv_background);
        Glide.with(this).load(R.drawable.background)
                .centerCrop()
                .into(mBackground);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addDrop:
                showAddDialog();
                break;
        }
    }

    private void showAddDialog() {
        AddDialog dialog = new AddDialog();
        dialog.show(getSupportFragmentManager(), "AddDialog");
        dialog.setCancelable(true);
    }
}
