package naufalJmartFA.jmart_android;

import androidx.appcompat.app.AppCompatActivity;


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
import naufalJmartFA.jmart_android.request.TopUpRequest;

public class AboutMeActivity extends AppCompatActivity {

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
        if (account.store == null){
            buttonRegister.setVisibility(View.VISIBLE);
        }
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
            editTopUp.setText("");

        });

    }
}