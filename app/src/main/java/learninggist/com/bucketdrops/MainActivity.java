package learninggist.com.bucketdrops;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import learninggist.com.bucketdrops.adapters.RecyclerAdapter;
import learninggist.com.bucketdrops.beans.Drop;
import learninggist.com.bucketdrops.widgets.CustomRecyclerView;


/**
 * Created by Deepak on 5/21/16.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ImageView mBackground;
    private Button mAddDropBtn;
    private CustomRecyclerView mRecyclerView;
    private Realm mRealm;
    private RealmResults<Drop> mResults;
    private RecyclerAdapter mAdapter;
    private View mEmptyView;
    private AddListener mAddListener = new AddListener() {
        @Override
        public void add() {
            showAddDialog();
        }
    };

    private DropListener mDropListener = new DropListener() {
        @Override
        public void MarkCompleted(int position) {
            showMarkDialog(position);
        }
    };

    private MarkCompleteListener mMarkCompleteListener = new MarkCompleteListener() {
        @Override
        public void MarkCompleted(int position) {
            mAdapter.setCompleted(position);
        }
    };

    private void showMarkDialog(int position) {
        MarkDialog markDialog = new MarkDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        markDialog.setMarkCompletedListener(mMarkCompleteListener);
        markDialog.setArguments(bundle);
        markDialog.show(getSupportFragmentManager(), "MARK");
        markDialog.setCancelable(true);

    }


    private RealmChangeListener
            mRealmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            Log.d("Deepak", "onChange: was called" + element);
            mAdapter.update(mResults);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mRealm = Realm.getDefaultInstance();
        loadResults(load());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEmptyView = findViewById(R.id.emptyView);
        mAddDropBtn = (Button) findViewById(R.id.btn_addDrop);
        mRecyclerView = (CustomRecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.hideIfEmpty(mToolbar);
        mRecyclerView.showIfEmpty(mEmptyView);
        mAdapter = new RecyclerAdapter(this, mRealm, mResults, mAddListener, mDropListener);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAddDropBtn.setOnClickListener(this);
        setSupportActionBar(mToolbar);
        SimpleSwipeCallback callback = new SimpleSwipeCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
        setLayoutBackground();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mResults.addChangeListener(mRealmChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mResults.removeChangeListener(mRealmChangeListener);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean handled = true;
        int filterOption = FilterOptions.NONE;
        switch (id) {
            case R.id.menu_add:
                showAddDialog();
            case R.id.menu_descending:
                save(FilterOptions.MOST_TIME_LEFT);
                filterOption = (FilterOptions.MOST_TIME_LEFT);
                break;
            case R.id.menu_ascending:
                save(FilterOptions.LEAST_TIME_LEFT);
                filterOption = (FilterOptions.LEAST_TIME_LEFT);
                break;
            case R.id.menu_complete:
                save(FilterOptions.COMPLETE);
                filterOption = (FilterOptions.COMPLETE);
                break;
            case R.id.menu_incomplete:
                save(FilterOptions.INCOMPLETE);
                filterOption = (FilterOptions.INCOMPLETE);
                break;
            default:
                handled = false;
                break;
        }
        loadResults(filterOption);
        return handled;
    }

    public void save(int filter) {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("filter", filter);
        editor.apply();
    }

    public int load() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        return pref.getInt("filter", FilterOptions.NONE);
    }

    private void loadResults(int filter) {
        switch (filter) {
            case FilterOptions.NONE:
                mResults = mRealm.where(Drop.class).findAllAsync();
                break;
            case FilterOptions.MOST_TIME_LEFT:
                save(FilterOptions.MOST_TIME_LEFT);
                mResults = mRealm.where(Drop.class).findAllSortedAsync("endTime", Sort.DESCENDING);
                break;
            case FilterOptions.LEAST_TIME_LEFT:
                save(FilterOptions.LEAST_TIME_LEFT);
                mResults = mRealm.where(Drop.class).findAllSortedAsync("endTime", Sort.ASCENDING);
                break;
            case FilterOptions.COMPLETE:
                save(FilterOptions.COMPLETE);
                mResults = mRealm.where(Drop.class).equalTo("isCompleted", true).findAllAsync();
                break;
            case FilterOptions.INCOMPLETE:
                save(FilterOptions.INCOMPLETE);
                mResults = mRealm.where(Drop.class).equalTo("isCompleted", false).findAllAsync();
                break;
        }
        mResults.addChangeListener(mRealmChangeListener);
    }
}
