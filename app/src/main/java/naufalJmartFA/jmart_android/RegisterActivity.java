package naufalJmartFA.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import naufalJmartFA.jmart_android.request.RegisterRequest;

/**
 * Register Activity for registration
 * @author Muhammad Naufal Faza
 */
public class RegisterActivity extends AppCompatActivity {


    /**
     * Method onCreate is launched when the activity is launched for the first time.
     * @param savedInstanceState ...
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText editName = findViewById(R.id.editTextTextPersonName);
        EditText editEmail = findViewById(R.id.editTextTextEmailAddress2);
        EditText editPassword = findViewById(R.id.editTextTextPassword2);
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                try{
                    JSONObject object = new JSONObject(response);
                    if (object != null){
                        Toast.makeText(RegisterActivity.this,"Register Success!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Error Register", Toast.LENGTH_LONG).show();
                }
            };
            Response.ErrorListener errorListener = error -> Toast.makeText(RegisterActivity.this,"Error Register", Toast.LENGTH_LONG);
            RegisterRequest registerRequest = new RegisterRequest(editName.getText().toString(),
                    editEmail.getText().toString(),editPassword.getText().toString(),
                    listener, errorListener);
            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
            requestQueue.add(registerRequest);
        });
    }
}