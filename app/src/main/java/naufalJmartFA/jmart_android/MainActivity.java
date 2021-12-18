package naufalJmartFA.jmart_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;

import naufalJmartFA.jmart_android.model.Account;
import naufalJmartFA.jmart_android.model.Product;
import naufalJmartFA.jmart_android.model.ProductCategory;
import naufalJmartFA.jmart_android.request.RequestFactory;


public class MainActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    public static ArrayList<Product> products = new ArrayList<>();
    private ArrayAdapter<Product> adapter;
    private ListView listView;
    private RadioGroup conditionGroup;
    private boolean conditionUsed;
    private ProductCategory selectedProductCategory;
    private RadioButton usedOrNew;
    private int selectedTab;
    public static int selectedProduct;
    private ListView productListView;
    private EditText nameFilter;
    private EditText lowestPriceFilter;
    private EditText highestPriceFilter;
    private EditText pageEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Account account = LoginActivity.getLoggedAccount();
        TabLayout tabLayOut = findViewById(R.id.tab);
        CardView filterCard = findViewById(R.id.filterCard);
        CardView productCard = findViewById(R.id.productCard);
        tabLayOut.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tabLayOut.getSelectedTabPosition();
                if (selectedTab == 1){
                    filterCard.setVisibility(View.VISIBLE);
                    productCard.setVisibility(View.GONE);
                }else{
                    filterCard.setVisibility(View.GONE);
                    productCard.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Card untuk filter
        conditionGroup = findViewById(R.id.radioConditionFilter);
        nameFilter = findViewById(R.id.nameFilter);
        lowestPriceFilter = findViewById(R.id.lowestPriceFilter);
        highestPriceFilter = findViewById(R.id.highestPriceFilter);
        Spinner spinnerCategory = findViewById(R.id.spinnerCategoryFilter);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,R.array.spinnerCategory, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
        selectedProductCategory = ProductCategory.ART_CRAFT;
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
        Button applyFilterButton = findViewById(R.id.applyFilter);
        applyFilterButton.setOnClickListener(v -> {
            int page = Integer.parseInt(pageEditText.getText().toString()) - 1;
            populateListView(page);
        });
        Button clearFilterButton = findViewById(R.id.clearFilter);
        clearFilterButton.setOnClickListener(v -> {
            nameFilter.setText("");
            lowestPriceFilter.setText("");
            highestPriceFilter.setText("");
            usedOrNew.setChecked(false);
        });

        //Card untuk Product
        pageEditText = findViewById(R.id.pageEditText);
        Button prev = findViewById(R.id.buttonPrevProduct);
        prev.setOnClickListener(v ->{
            pageEditText.setText(String.format("%d",Integer.parseInt(pageEditText.getText().toString())-1));
            int page = Integer.parseInt(pageEditText.getText().toString()) - 1;
            populateListView(page);
        });
        Button next = findViewById(R.id.buttonNextProduct);
        next.setOnClickListener(v ->{
            pageEditText.setText(String.format("%d",Integer.parseInt(pageEditText.getText().toString())+1));
            int page = Integer.parseInt(pageEditText.getText().toString()) - 1;
            populateListView(page);
        });
        Button go = findViewById(R.id.buttonGoProduct);
        go.setOnClickListener(v -> {
            int page = Integer.parseInt(pageEditText.getText().toString()) - 1;
            populateListView(page);
        });
        productListView = findViewById(R.id.productListView);
        Response.Listener<String> listener = response -> {
            try{
                products.clear();
                JSONArray jsonArray = new JSONArray(response);
                Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                products = gson.fromJson(String.valueOf(jsonArray), type);
                adapter = new ArrayAdapter<Product>(getApplicationContext(), android.R.layout.simple_list_item_1, products);
                productListView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
        try{
            Response.ErrorListener errorListener = error -> Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
            StringRequest productRequest = RequestFactory.getProduct(Integer.parseInt(pageEditText.getText().toString())-1,
                    8,conditionUsed,nameFilter.getText().toString(),Double.parseDouble(lowestPriceFilter.getText().toString()),
                    Double.parseDouble(highestPriceFilter.getText().toString()),selectedProductCategory,listener,errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(productRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error happened",Toast.LENGTH_LONG).show();
        }
        productListView.setOnItemClickListener((parent,view,position, id) -> {
            selectedProduct = position;
            Intent toDetailProducts = new Intent (MainActivity.this, ProductDetailActivity.class);
            startActivity(toDetailProducts);
        });

    }

    /**
     * Method to populate the list view by getting product filtered from productTable
     * @param page which page does the user want
     */
    public void populateListView (int page){
        Response.Listener<String> listener = response -> {
          try{
              products.clear();
              JSONArray jsonArray = new JSONArray(response);
              Type type = new TypeToken<ArrayList<Product>>(){}.getType();
              products = gson.fromJson(String.valueOf(jsonArray), type);
              adapter = new ArrayAdapter<Product>(getApplicationContext(), android.R.layout.simple_list_item_1, products);
              productListView.setAdapter(adapter);
            } catch (JSONException e) {
              e.printStackTrace();
              Toast.makeText(MainActivity.this, "The filter has failed", Toast.LENGTH_LONG).show();
          }
        };
        try{
            Response.ErrorListener errorListener = error -> Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
            StringRequest productRequest = RequestFactory.getProduct(Integer.parseInt(pageEditText.getText().toString())-1,
                    8,conditionUsed,nameFilter.getText().toString(),Double.parseDouble(lowestPriceFilter.getText().toString()),
                    Double.parseDouble(highestPriceFilter.getText().toString()),selectedProductCategory,listener,errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(productRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error happened",Toast.LENGTH_LONG).show();
        }
    }


    /**
     * To create Menu
     * @param menu parameter of menu
     * @return always return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        Account account = LoginActivity.getLoggedAccount();

        MenuItem createProduct = menu.getItem(1);
        createProduct.setVisible(account.store != null);
        return true;
    }

    /**
     * Decide what will happen when Menu is selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent createProductIntent = new Intent(MainActivity.this, CreateProductActivity.class);
        Intent aboutMeIntent = new Intent(MainActivity.this,AboutMeActivity.class);
        Intent backtoLogin = new Intent(MainActivity.this, LoginActivity.class);
        backtoLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        switch (item.getItemId()){
            case R.id.search_menu:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_box_menu:
                Toast.makeText(this, "Add box", Toast.LENGTH_SHORT).show();
                startActivity(createProductIntent);
                return true;
            case R.id.person_menu:
                Toast.makeText(this, "About me", Toast.LENGTH_SHORT).show();
                startActivity(aboutMeIntent);
                return true;
            case R.id.logout_menu:
                Toast.makeText(this, "Logout Success", Toast.LENGTH_SHORT).show();
//                SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
//                sessionManagement.removeSession();
                LoginActivity.fetchAccount(null);
                startActivity(backtoLogin);
            default:
                return super.onOptionsItemSelected(item);
        }

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