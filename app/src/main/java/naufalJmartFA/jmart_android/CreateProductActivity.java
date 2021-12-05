package naufalJmartFA.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateProductActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

//        String[] category = {"ART_CRAFT", "AUTOMOTIVE", "BOOK", "CARPENTRY", "COSMETICS", "ELECTRONIC",
//                "FASHION", "FNB", "FURNITURE", "GADGET", "GAMING", "HEALTHCARE", "JEWELRY",
//                "KITCHEN", "MISCELLANEOUS", "MOTHERCARE", "PETCARE", "PROPERTY", "SPORTS",
//                "STATIONERY", "TOYS", "TRAVEL", "WEDDING"};
//        String[] shipmentPlan = {"INSTANT", "SAME DAY", "NEXT DAY", "REGULER", "KARGO"};
//
//        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
//        ArrayAdapter adapterCategory = new ArrayAdapter(this, android.R.layout.simple_spinner_item,category);
//        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategory.setAdapter(adapterCategory);
//        spinnerCategory.setOnItemSelectedListener(this);
//
//        Spinner spinnerShipmentPlan = findViewById(R.id.spinnerShipmentPlan);
//        ArrayAdapter adapterShipmentPlan = new ArrayAdapter(this, android.R.layout.simple_spinner_item,shipmentPlan);
//        adapterShipmentPlan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerShipmentPlan.setAdapter(adapterShipmentPlan);
//        spinnerShipmentPlan.setOnItemSelectedListener(this);

    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String text = parent.getItemAtPosition(position).toString() + " selected";
//        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}