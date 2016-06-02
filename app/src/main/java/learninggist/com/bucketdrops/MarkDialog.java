package learninggist.com.bucketdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Deepak on 6/1/16.
 */
public class MarkDialog extends DialogFragment {

    private ImageButton mBtnClose;
    private Button mBtnCompleted;
    private MarkCompleteListener mListener;

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_completed:
                    setMarkCompleted();
                    break;
            }
            dismiss();
        }
    };

    private void setMarkCompleted() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            int position = bundle.getInt("POSITION");
            mListener.MarkCompleted(position);
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.completed_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClose = (ImageButton) view.findViewById(R.id.btn_close);
        mBtnCompleted = (Button) view.findViewById(R.id.btn_completed);
        mBtnClose.setOnClickListener(mClickListener);
        mBtnCompleted.setOnClickListener(mClickListener);

    }

    public void setMarkCompletedListener(MarkCompleteListener markCompleteListener) {
        mListener = markCompleteListener;
    }
}
