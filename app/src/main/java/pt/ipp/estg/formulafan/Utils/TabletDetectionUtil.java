package pt.ipp.estg.formulafan.Utils;

import android.content.Context;
import android.content.res.Configuration;

public class TabletDetectionUtil {

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
