package imabi.no.kyo.com.goodness.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by gui-wani on 20/11/2015.
 */
public class SettingsGetter {

    private Context context;
    private SharedPreferences sp;

    public SettingsGetter(Context context) {
        this.context = context;
        this.sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isNotificationSuggestionsEnabled(){
        return sp.getBoolean("pref_notification_enable_suggestions", true);
    }
}
