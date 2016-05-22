package learninggist.com.bucketdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import learninggist.com.bucketdrops.beans.Drop;


/**
 * Created by Deepak on 5/21/16.
 */
public class AddDialog extends DialogFragment implements View.OnClickListener {

    private EditText mEtWhat;
    private Button mBtnAddDrop;
    private ImageButton mDialogClose;
    private DatePicker mDatePicker;

    public AddDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.add_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEtWhat = (EditText) view.findViewById(R.id.et_addTitle);
        mBtnAddDrop = (Button) view.findViewById(R.id.btn_addDrop);
        mDialogClose = (ImageButton) view.findViewById(R.id.ib_close);
        mDatePicker = (DatePicker) view.findViewById(R.id.datepicker);

        mDialogClose.setOnClickListener(this);
        mBtnAddDrop.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addDrop:
                if (validateDrop()) {
                    addDrop();
                }
                break;
        }
        dismiss();

    }

    private void addDrop() {
        RealmConfiguration config = new RealmConfiguration.Builder(getActivity()).build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();
        Drop drop = new Drop(mEtWhat.getText().toString(), System.currentTimeMillis(), 0, false);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(drop);
        realm.commitTransaction();
        realm.close();
    }

    private boolean validateDrop() {
        if (mEtWhat != null && mEtWhat.getText().length() != 0)
            return true;
        return false;
    }
}
