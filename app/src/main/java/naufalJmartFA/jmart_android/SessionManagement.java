package naufalJmartFA.jmart_android;

import android.content.Context;
import android.content.SharedPreferences;

import naufalJmartFA.jmart_android.model.Account;

/**
 * Session management
 * @author Muhammad Naufal Faza
 * @deprecated Not used because there is a simpler way to manage session.
 */
public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    /**
     * Default constructor for sessionManagement
     * @param context which activity context
     */
    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * When logged in, saveSession should be called
     * @param account account credential for ID
     */
    public void saveSession (Account account){
        editor.putInt(SESSION_KEY, account.id+1).commit();
    }

    /**
     * Get session to see if session already saved or not
     * @return 0 if session isn't saved. Else, account.id + 1
     */
    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY,0);
    }

    /**
     * Called when logged out
     */
    public void removeSession(){
        editor.putInt(SESSION_KEY, 0).commit();
    }

}
