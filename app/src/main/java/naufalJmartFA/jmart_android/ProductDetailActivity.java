package naufalJmartFA.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import naufalJmartFA.jmart_android.model.Account;
import naufalJmartFA.jmart_android.model.Product;
import naufalJmartFA.jmart_android.request.LoginRequest;
import naufalJmartFA.jmart_android.request.PaymentRequest;

/**
 * Product Detail Activity class
 */
public class ProductDetailActivity extends AppCompatActivity {

    /**
     * Method onCreate for ProductDetailActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        TextView detailProductName = findViewById(R.id.detailProductName);
        TextView category = findViewById(R.id.realDetailCategory);
        TextView condition = findViewById(R.id.realDetailCondition);
        TextView weight = findViewById(R.id.realDetailWeight);
        TextView price = findViewById(R.id.realDetailPrice);
        TextView discount = findViewById(R.id.realDetailDiscount);
        TextView shipmentPlan = findViewById(R.id.realDetailShipmentPlans);

        Product choosenProduct = MainActivity.products.get(MainActivity.selectedProduct);
        detailProductName.setText(choosenProduct.name);
        category.setText(choosenProduct.category.toString());
        if (!choosenProduct.conditionUsed){
            condition.setText("NEW");
        }else{
            condition.setText("USED");
        }
        weight.setText(Integer.toString(choosenProduct.weight));
        price.setText(Double.toString(choosenProduct.price));
        discount.setText(Double.toString(choosenProduct.discount));
        String shipment = "INSTANT";
        if (choosenProduct.shipmentPlans == 1){
            shipment = "INSTANT";
        } else if (choosenProduct.shipmentPlans == 2){
            shipment = "SAME DAY";
        } else if(choosenProduct.shipmentPlans == 4){
            shipment = "NEXT DAY";
        } else if (choosenProduct.shipmentPlans == 8){
            shipment = "REGULER";
        } else if (choosenProduct.shipmentPlans == 16){
            shipment = "KARGO";
        }
        shipmentPlan.setText(shipment);

        Button cancelButton = findViewById(R.id.buttonCancelBuyProduct);
        cancelButton.setOnClickListener(v -> finish());

        Button buyButton = findViewById(R.id.buttonBuyProduct);
        buyButton.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                try{
                    JSONObject object = new JSONObject(response);
                    if (object != null){
                        Toast.makeText(ProductDetailActivity.this,"Payment Success!",Toast.LENGTH_LONG).show();

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(ProductDetailActivity.this,"Payment Error!", Toast.LENGTH_LONG).show();
                }
            };
            Response.ErrorListener errorListener = error -> Toast.makeText(ProductDetailActivity.this,"Payment Error", Toast.LENGTH_LONG).show();
            PaymentRequest paymentRequest = new PaymentRequest(LoginActivity.getLoggedAccount().id, choosenProduct.id, 1,
                    LoginActivity.getLoggedAccount().store.address,choosenProduct.shipmentPlans,listener,errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(ProductDetailActivity.this);
            requestQueue.add(paymentRequest);
        });
    }

}