package learninggist.com.bucketdrops.extras;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import java.util.List;

/**
 * Created by Deepak on 5/21/16.
 */
public class Util {

    public static void showViews(List<View>  views){
        for (View view: views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViews(List<View> views){
        for (View view: views) {
            view.setVisibility(View.GONE);
        }
    }

    public static void setBackground(View itemView, Drawable drawable) {
        if(isGreaterThan15()){
            itemView.setBackground(drawable);
        }
        else{
            itemView.setBackgroundDrawable(drawable);
        }
    }

    private static boolean isGreaterThan15() {
        return Build.VERSION.SDK_INT >15;
    }
}
