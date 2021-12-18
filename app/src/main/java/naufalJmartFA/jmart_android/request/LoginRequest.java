package naufalJmartFA.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * LoginRequest so that can login to backend
 * @author Muhammad Naufal Faza
 */
public class LoginRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:6969/account/login";
    private final Map<String,String> params;

    /**
     * Main constructor
     * @param email email of the account
     * @param password password of the account
     * @param listener listener from front end
     * @param errorListener errorListener from front end
     */
    public LoginRequest (String email, String password, Response.Listener<String> listener,
                         Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    /**
     * getter for params
     * @return params
     */
    public Map<String,String> getParams(){
        return params;
    }
}