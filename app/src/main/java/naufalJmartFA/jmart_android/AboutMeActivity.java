package naufalJmartFA.jmart_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import naufalJmartFA.jmart_android.model.Account;
import naufalJmartFA.jmart_android.request.LoginRequest;
import naufalJmartFA.jmart_android.request.RegisterStoreRequest;
import naufalJmartFA.jmart_android.request.TopUpRequest;

/**
 * Activity in about me (person logo)
 * @author Muhammad Naufal Faza
 */
public class AboutMeActivity extends AppCompatActivity {

    /**
     * on created layour
     * @param savedInstanceState instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        Account account = LoginActivity.getLoggedAccount();
        TextView nameAccount = findViewById(R.id.nameAccount);
        TextView emailAccount = findViewById(R.id.emailAccount);
        TextView balanceAccount = findViewById(R.id.balanceAccount);

        nameAccount.setText(account.name);
        emailAccount.setText(account.email);
        balanceAccount.setText(Double.toString(account.balance));

        EditText editTopUp = findViewById(R.id.editTopUpAmount);

        Button buttonTopUp = findViewById(R.id.buttonTopUp);
        Button buttonRegister = findViewById(R.id.buttonRegisterStore);
        CardView registerCardView = findViewById(R.id.RegisterCardView);
        TextView registerStoreText = findViewById(R.id.RegisterStoreText);
        EditText registerStoreName = findViewById(R.id.RegisterStoreName);
        EditText registerStoreAddress = findViewById(R.id.RegisterStoreAddress);
        EditText registerStorePhoneNumber = findViewById(R.id.RegisterPhoneNumber);
        TextView storeText = findViewById(R.id.storeText);
        TextView textStoreName = findViewById(R.id.textStoreName);
        TextView textStoreAddress = findViewById(R.id.textStoreAddress);
        TextView textStorePhoneNumber = findViewById(R.id.textStorePhoneNumber);
        TextView realStoreName = findViewById(R.id.realStoreName);
        TextView realStoreAddress = findViewById(R.id.realStoreAddress);
        TextView realStorePhoneNumber = findViewById(R.id.realStorePhoneNumber);
        Button buttonRealRegister = findViewById(R.id.buttonRegisterStoreReal);
        Button buttonCancel = findViewById(R.id.buttonCancelRegisterStore);
        if (account.store == null){
            buttonRegister.setVisibility(View.VISIBLE);
            registerCardView.setVisibility(View.INVISIBLE);
        }else{
            registerCardView.setVisibility(View.VISIBLE);
            registerStoreText.setVisibility(View.INVISIBLE);
            registerStoreName.setVisibility(View.INVISIBLE);
            registerStoreAddress.setVisibility(View.INVISIBLE);
            registerStorePhoneNumber.setVisibility(View.INVISIBLE);
            buttonRegister.setVisibility(View.INVISIBLE);
            buttonRealRegister.setVisibility(View.INVISIBLE);
            buttonCancel.setVisibility(View.INVISIBLE);
            realStoreName.setText(LoginActivity.getLoggedAccount().store.name);
            realStoreAddress.setText(LoginActivity.getLoggedAccount().store.address);
            realStorePhoneNumber.setText(LoginActivity.getLoggedAccount().store.phoneNumber);

        }
        buttonRegister.setOnClickListener( v -> {
            buttonRegister.setVisibility(View.INVISIBLE);
            registerCardView.setVisibility(View.VISIBLE);
            storeText.setVisibility(View.INVISIBLE);
            textStoreName.setVisibility(View.INVISIBLE);
            textStoreAddress.setVisibility(View.INVISIBLE);
            textStorePhoneNumber.setVisibility(View.INVISIBLE);
            realStoreName.setVisibility(View.INVISIBLE);
            realStoreAddress.setVisibility(View.INVISIBLE);
            realStorePhoneNumber.setVisibility(View.INVISIBLE);

        });
        buttonCancel.setOnClickListener(v -> {
            registerStoreName.setText("");
            registerStoreAddress.setText("");
            registerStorePhoneNumber.setText("");
            registerCardView.setVisibility(View.INVISIBLE);
            buttonRegister.setVisibility(View.VISIBLE);
        });

        buttonRealRegister.setOnClickListener(v ->{
            Response.Listener<String> listener = response -> {
                try{
                    JSONObject object = new JSONObject(response);
                    if (object != null){
                        Toast.makeText(AboutMeActivity.this,"Store Registration Success! Please relogin to update your data",Toast.LENGTH_LONG).show();
                        Intent afterRegisterStore = new Intent(AboutMeActivity.this, LoginActivity.class);
                        startActivity(afterRegisterStore);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AboutMeActivity.this, "Error Login", Toast.LENGTH_LONG).show();
                }
            };
            Response.ErrorListener errorListener = error -> Toast.makeText(AboutMeActivity.this,"Error Login", Toast.LENGTH_LONG);
            RegisterStoreRequest registerStoreRequest = new RegisterStoreRequest(LoginActivity.getLoggedAccount().id,registerStoreName.getText().toString(),
                    registerStoreAddress.getText().toString(),registerStorePhoneNumber.getText().toString(), listener, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
            requestQueue.add(registerStoreRequest);
            registerStoreName.setText("");
            registerStoreAddress.setText("");
            registerStorePhoneNumber.setText("");
        });
        buttonTopUp.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                if (response.equals("true")){
                    Toast.makeText(AboutMeActivity.this, "Top Up Sukses", Toast.LENGTH_LONG).show();

                }
            };
            Response.ErrorListener errorListener = error -> Toast.makeText(AboutMeActivity.this,"Top Up Error", Toast.LENGTH_LONG);
            TopUpRequest topUpRequest = new TopUpRequest(account.id,Double.parseDouble(editTopUp.getText().toString()),listener,errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
            requestQueue.add(topUpRequest);
            balanceAccount.setText(Double.toString(Double.parseDouble(balanceAccount.getText().toString())+Double.parseDouble(editTopUp.getText().toString())));
            account.balance += Double.parseDouble(editTopUp.getText().toString());
            LoginActivity.fetchAccount(account);
            editTopUp.setText("");

        });

    }
}