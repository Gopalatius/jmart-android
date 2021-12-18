package naufalJmartFA.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * RegisterRequest class for so user can register
 * @author Muhammad Naufal Faza
 */
public class RegisterRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:6969/account/register";
    private final Map<String,String> params;

    /**
     * Main constructor
     * @param name name of the account
     * @param email email of the account
     * @param password password of the account
     * @param listener listener from front end
     * @param errorListener error listener from front end
     */
    public RegisterRequest(String name, String email, String password,
                           Response.Listener<String> listener,
                           Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
    }
    public Map<String,String> getParams(){
        return params;
    }
}

