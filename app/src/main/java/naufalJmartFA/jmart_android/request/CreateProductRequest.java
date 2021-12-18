package naufalJmartFA.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import naufalJmartFA.jmart_android.model.ProductCategory;

/**
 * Request CreateProduct by user who has store
 * @author Muhammad Naufal Faza
 */
public class CreateProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:6969/product/create";
    private final Map<String,String> params;

    /**
     * Main constructor
     * @param accountId ID of the user
     * @param name name of the product
     * @param weight weight of the product
     * @param conditionUsed condition of the product
     * @param price price of the product
     * @param discount discount of the product
     * @param category category of the product
     * @param shipmentPlans shipmentPlans of the product
     * @param listener listener
     * @param errorListener error Listener
     */
    public CreateProductRequest(int accountId, String name, int weight, boolean conditionUsed,
                                double price, double discount, ProductCategory category,
                                byte shipmentPlans, Response.Listener<String> listener,
                        Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("accountId", Integer.toString(accountId));
        params.put("name", name);
        params.put("weight", Integer.toString(weight));
        params.put("conditionUsed", Boolean.toString(conditionUsed));
        params.put("price", Double.toString(price));
        params.put("discount", Double.toString(discount));
        params.put("category", category.toString());
        params.put("shipmentPlans", Byte.toString(shipmentPlans));

    }

    /**
     * getter for params
     * @return params
     */
    public Map<String,String> getParams(){
        return params;
    }
}
