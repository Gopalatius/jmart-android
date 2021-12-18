package naufalJmartFA.jmart_android.request;



import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to RegisterStoreRequest
 * @author Muhammad Naufal Faza
 */
public class RegisterStoreRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:6969/account/%d/registerStore";
    private final Map<String,String> params;

    /**
     * Main constructor
     * @param id id of the account
     * @param name name of the store
     * @param address address of the store
     * @param phoneNumber phone number of the store
     * @param listener listener from front end
     * @param errorListener error Listener from front end
     */
    public RegisterStoreRequest(int id, String name, String address, String phoneNumber,
                                Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST, String.format(URL,id),listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);
    }
    public Map<String,String> getParams() { return params; }
}
