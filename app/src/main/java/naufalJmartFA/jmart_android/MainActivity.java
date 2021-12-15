package naufalJmartFA.jmart_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import naufalJmartFA.jmart_android.model.Account;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        Account account = LoginActivity.getLoggedAccount();

        if (account.store == null){
            MenuItem addBox = findViewById(R.id.add_box_menu);
            addBox.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.search_menu:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_box_menu:
                Toast.makeText(this, "Add box", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.person_menu:
                Toast.makeText(this, "About me", Toast.LENGTH_SHORT).show();
                Intent aboutMeIntent = new Intent(MainActivity.this,AboutMeActivity.class);
                startActivity(aboutMeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}