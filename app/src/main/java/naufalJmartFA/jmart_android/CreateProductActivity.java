package naufalJmartFA.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import naufalJmartFA.jmart_android.model.ProductCategory;
import naufalJmartFA.jmart_android.request.CreateProductRequest;
import naufalJmartFA.jmart_android.request.LoginRequest;


public class CreateProductActivity extends AppCompatActivity {
    private RadioGroup conditionGroup;
    private RadioButton usedOrNew;
    private boolean conditionUsed;
    private ProductCategory selectedProductCategory;
    private byte byteShipmentPlans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        EditText productName = findViewById(R.id.editName);
        EditText productWeight = findViewById(R.id.editWeight);
        EditText productPrice = findViewById(R.id.editPrice);
        EditText productDiscount = findViewById(R.id.editDiscount);
        conditionGroup = findViewById(R.id.radioConditionGroup);

        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,R.array.spinnerCategory, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                selectedProductCategory = ProductCategory.valueOf(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spinnerShipmentPlans = findViewById(R.id.spinnerShipmentPlan);
        ArrayAdapter<CharSequence> shipmentPlansAdapter = ArrayAdapter.createFromResource(this,R.array.spinnerShipmentPlan, android.R.layout.simple_spinner_item);
        shipmentPlansAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShipmentPlans.setAdapter(shipmentPlansAdapter);
        spinnerShipmentPlans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedShipmentPlans = parent.getItemAtPosition(position).toString();
                switch (selectedShipmentPlans) {
                    case "INSTANT":
                        byteShipmentPlans = 1;
                        break;
                    case "SAME DAY":
                        byteShipmentPlans = 2;
                        break;
                    case "NEXT DAY":
                        byteShipmentPlans = 4;
                        break;
                    case "REGULER":
                        byteShipmentPlans = 8;
                        break;
                    case "KARGO":
                        byteShipmentPlans = 16;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> startActivity(new Intent(CreateProductActivity.this, MainActivity.class)));

        Button createButton = findViewById(R.id.createButton);

        createButton.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                try{
                    JSONObject object = new JSONObject(response);
                    if (object != null){
                        Toast.makeText(CreateProductActivity.this,"Create Product Success!",Toast.LENGTH_LONG).show();
                        Intent backtoMain = new Intent(CreateProductActivity.this, MainActivity.class);
                        startActivity(backtoMain);
                        finish();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(CreateProductActivity.this, "Create Product Error", Toast.LENGTH_LONG).show();
                }
            };

            Response.ErrorListener errorListener = error -> Toast.makeText(CreateProductActivity.this,"Create Product Error", Toast.LENGTH_LONG).show();
            CreateProductRequest createProductRequest = new CreateProductRequest(LoginActivity.getLoggedAccount().id,productName.getText().toString(),
                    Integer.parseInt(productWeight.getText().toString()),conditionUsed,Double.parseDouble(productPrice.getText().toString()),
                    Double.parseDouble(productDiscount.getText().toString()),selectedProductCategory,byteShipmentPlans,listener,errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(CreateProductActivity.this);
            requestQueue.add(createProductRequest);


        });

    }
    public void onClickCondition (View view){
        usedOrNew = findViewById(conditionGroup.getCheckedRadioButtonId());
        if (usedOrNew.getText().toString().equals("NEW")){
            conditionUsed = false;
        }else if(usedOrNew.getText().toString().equals("USED")){
            conditionUsed = true;
        }
    }


}