package naufalJmartFA.jmart_android;

import android.content.Context;
import android.content.SharedPreferences;

import naufalJmartFA.jmart_android.model.Account;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession (Account account){
        editor.putInt(SESSION_KEY, account.id+1).commit();
    }

    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY,0);
    }


    public void removeSession(){
        editor.putInt(SESSION_KEY, 0).commit();
    }

}
