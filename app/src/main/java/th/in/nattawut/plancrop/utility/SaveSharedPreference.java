package th.in.nattawut.plancrop.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static th.in.nattawut.plancrop.utility.PreferencesUtility.LOGGED_IN_PREF;

public class SaveSharedPreference {

    static SharedPreferences getPerferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context,boolean loggedIn){
        SharedPreferences.Editor editor = getPerferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF,loggedIn);
        editor.apply();
    }
    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context){
        return getPerferences(context).getBoolean(LOGGED_IN_PREF,false);
    }
}
