package naufalJmartFA.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TopUpRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:6969/account/%d/topUp";
    private final Map<String,String> params;

    public TopUpRequest(int id, double balance,
                           Response.Listener<String> listener,
                           Response.ErrorListener errorListener)
    {
        super(Method.POST, String.format(URL,id), listener, errorListener);
        params = new HashMap<>();
        params.put("balance", Double.toString(balance));

    }
    public Map<String,String> getParams(){
        return params;
    }
}