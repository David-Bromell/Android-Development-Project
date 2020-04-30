package ie.ul.tutorfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class RequestsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        addActionBar();
    }

    private void loginRedirect(){
        Intent startIntent = new Intent(RequestsActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void addActionBar(){
        mToolbar = findViewById(R.id.profile_page_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Tutor Finder - Profile");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout_button){
            FirebaseAuth.getInstance().signOut();
            loginRedirect();
        }

        return true;
    }
}
