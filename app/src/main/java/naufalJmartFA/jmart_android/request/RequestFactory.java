package naufalJmartFA.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import naufalJmartFA.jmart_android.model.ProductCategory;

/**
 * RequestFactory for GET method
 * @author Muhammad Naufal Faza
 */
public class RequestFactory {
    private static final String URL_FORMAT_ID = "http://10.0.2.2:6969/%s/%d";
    private static final String URL_FORMAT_PAGE= "http://10.0.2.2:6969/%s/page?page=%s&pageSize=%s";
    private static final String URL_FORMAT_PRODUCT = "http://10.0.2.2:6969/product/getFiltered?page=%s&pageSize=%s&conditionUsed=%s&search=%s&minPrice=%s&maxPrice=%s&category=%s";

    /**
     * GET stringRequest by ID
     * @param parentURI name of the controller
     * @param id id of the account
     * @param listener listener from front end
     * @param errorListener error listener from front end
     * @return StringRequest of get method with url, listener, and error listener
     */
    public static StringRequest getById(
            String parentURI,
            int id,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener)
    {
        String url = String.format(URL_FORMAT_ID, parentURI, id);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);

    }

    /**
     * String request for PageRequest
     *  @param parentURI name of the controller
     *  @param page wanted page
     * @param pageSize wanted page size
     *  @param listener listener from front end
     *  @param errorListener error listener from front end
     *  @return StringRequest of get method with url, listener, and error listener
     */
    public static StringRequest getPage(
            String parentURI,
            int page,
            int pageSize,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    )
    {
        String url = String.format(URL_FORMAT_PAGE,parentURI,page,pageSize);
//        Map<String,String> params = new HashMap<>();
//        params.put("page", String.valueOf(page));
//        params.put("pageSize", String.valueOf(pageSize));
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    /**
     * Method of getting product to ListView
     * @param page which page wanted
     * @param pageSize what page size wanted
     * @param conditionUsed is the product used or not
     * @param search search the name of the product
     * @param minPrice minimum price of product
     * @param maxPrice maximum price of product
     * @param category category of product
     * @param listener listener from front end
     * @param errorListener error listener from front end
     * @return StringRequest GET with parameter URL, listener, and errorListener
     */
    public static StringRequest getProduct(int page, int pageSize, boolean conditionUsed,
                                            String search, double minPrice, double maxPrice,
                                            ProductCategory category,
                                            Response.Listener<String> listener,
                                            Response.ErrorListener errorListener){
        String url = String.format(URL_FORMAT_PRODUCT,page,pageSize,conditionUsed,search,minPrice,maxPrice,category);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }



}
