package naufalJmartFA.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import naufalJmartFA.jmart_android.model.Account;
import naufalJmartFA.jmart_android.request.LoginRequest;

public class LoginActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    public static void fetchAccount(Account account) { loggedAccount = account;}

    public static Account getLoggedAccount(){
        return loggedAccount;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText emailLogin = findViewById(R.id.editTextTextEmailAddress);
        EditText passwordLogin = findViewById(R.id.editTextTextPassword);
        TextView registerNow = findViewById(R.id.registerNow);
        Button buttonLogin = findViewById((R.id.buttonLogin));
        buttonLogin.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                try{
                    JSONObject object = new JSONObject(response);
                    if (object != null){
                        Toast.makeText(LoginActivity.this,"Login Success!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        loggedAccount = gson.fromJson(object.toString(),Account.class);
                        startActivity(intent);
                        System.out.println(response);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Error Login", Toast.LENGTH_LONG).show();
                }
            };
            Response.ErrorListener errorListener = error -> Toast.makeText(LoginActivity.this,"Error Login", Toast.LENGTH_LONG);
            LoginRequest loginRequest = new LoginRequest(emailLogin.getText().toString(),
                    passwordLogin.getText().toString(), listener, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(loginRequest);
            emailLogin.setText("");
            passwordLogin.setText("");

        });
        registerNow.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }
}