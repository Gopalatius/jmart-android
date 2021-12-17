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


import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;



import java.util.ArrayList;

import naufalJmartFA.jmart_android.model.Account;
import naufalJmartFA.jmart_android.model.Product;
import naufalJmartFA.jmart_android.model.ProductCategory;


public class MainActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static ArrayList<Product> products = new ArrayList<>();
    private ArrayAdapter<Product> adapter;
    private ListView listView;
    private RadioGroup conditionGroup;
    private boolean conditionUsed;
    private ProductCategory selectedProductCategory;
    private RadioButton usedOrNew;
    private int selectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Account account = LoginActivity.getLoggedAccount();
        TabLayout tabLayOut = findViewById(R.id.tab);
        CardView filterCard = findViewById(R.id.filterCard);
        tabLayOut.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tabLayOut.getSelectedTabPosition();
                if (selectedTab == 1){
                    filterCard.setVisibility(View.VISIBLE);
                    //product card invisible
                }else{
                    filterCard.setVisibility(View.GONE);
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
        EditText nameFilter = findViewById(R.id.nameFilter);
        EditText lowestPriceFilter = findViewById(R.id.lowestPriceFilter);
        EditText highestPriceFilter = findViewById(R.id.highestPriceFilter);
        Spinner spinnerCategory = findViewById(R.id.spinnerCategoryFilter);
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
        Button applyFilterButton = findViewById(R.id.applyFilter);
        applyFilterButton.setOnClickListener(v -> {
            //akan melakukan paginasi
        });
        Button clearFilterButton = findViewById(R.id.clearFilter);
        clearFilterButton.setOnClickListener(v -> {
            nameFilter.setText("");
            lowestPriceFilter.setText("");
            highestPriceFilter.setText("");
            usedOrNew.setChecked(false);
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        Account account = LoginActivity.getLoggedAccount();

        MenuItem createProduct = menu.getItem(1);
        createProduct.setVisible(account.store != null);
        return true;
    }

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