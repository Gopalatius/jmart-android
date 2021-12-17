package naufalJmartFA.jmart_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import naufalJmartFA.jmart_android.model.Account;
import naufalJmartFA.jmart_android.request.LoginRequest;

public class MainActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Response.Listener<String> listener = response -> {
//            try{
//                JSONObject object = new JSONObject(response);
//                if (object != null){
//                    LoginActivity.fetchAccount(gson.fromJson(object.toString(),Account.class));
//                }
//            }catch (JSONException e){
//                e.printStackTrace();
//                Toast.makeText(MainActivity.this, "Error fetching", Toast.LENGTH_LONG).show();
//            }
//        };
//        Response.ErrorListener errorListener = error -> Toast.makeText(MainActivity.this,"Error fetching", Toast.LENGTH_LONG);
//        LoginRequest loginRequest = new LoginRequest(LoginActivity.getLoggedAccount().email,
//                LoginActivity.getLoggedAccount().password, listener, errorListener);
//        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//        requestQueue.add(loginRequest);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        Account account = LoginActivity.getLoggedAccount();

        MenuItem createProduct = menu.getItem(1);
        if (account.store == null){
            createProduct.setVisible(false);
        }else{
            createProduct.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent createProductIntent = new Intent(MainActivity.this, CreateProductActivity.class);
        Intent aboutMeIntent = new Intent(MainActivity.this,AboutMeActivity.class);
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}