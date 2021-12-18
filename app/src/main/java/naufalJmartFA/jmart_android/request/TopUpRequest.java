package naufalJmartFA.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * TopUpRequest so that user can topped up
 * @author Muhammad Naufal Faza
 */
public class TopUpRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:6969/account/%d/topUp";
    private final Map<String,String> params;

    /**
     * Main constructor
     * @param id id of the account
     * @param balance balance that wanted to be topped up
     * @param listener listener from front end
     * @param errorListener error listener from front end
     */
    public TopUpRequest(int id, double balance,
                           Response.Listener<String> listener,
                           Response.ErrorListener errorListener)
    {
        super(Method.POST, String.format(URL,id), listener, errorListener);
        params = new HashMap<>();
        params.put("balance", Double.toString(balance));

    }

    /**
     * Getter for params
     * @return params
     */
    public Map<String,String> getParams(){
        return params;
    }
}
