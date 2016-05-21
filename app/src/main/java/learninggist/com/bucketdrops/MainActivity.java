package learninggist.com.bucketdrops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


/**
 * Created by Deepak on 5/21/16.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ImageView mBackground;
    private Button mAddDropBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setLayoutBackground();
        mAddDropBtn = (Button) findViewById(R.id.btn_addDrop);
        mAddDropBtn.setOnClickListener(this);
    }

    private void setLayoutBackground() {
        mBackground = (ImageView) findViewById(R.id.iv_background);
        Glide.with(this).load(R.drawable.background)
                .centerCrop()
                .into(mBackground);
    }



    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btn_addDrop:
               Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
               break;
       }
    }
}
