package ie.ul.tutorfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

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

    private void addActionBar(){
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Tutor Finder - Home");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        addActionBar();

        Button myMessagesBtn = findViewById(R.id.MyMessagesBtn);
        myMessagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMessengerActivity();

            }
        });

        Button myProfileBtn1 = findViewById(R.id.MyProfileBtn);
        myProfileBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfileActivity();
            }

        });

        Button myLessonsBtn1 = findViewById(R.id.MyLessonsBtn);
        myLessonsBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyLessonsActivity();
            }

        });

        Button myTutorsBtn1 = findViewById(R.id.MyTutorsBtn);
        myTutorsBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyTutorsActivity();
            }

        });

        Button findTutorBtn1 = findViewById(R.id.FindTutorsBtn);
        findTutorBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFindTutorsActivity();
            }
        });

        Button connectBtn = findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConnectActivity();
            }
        });
    }

    // activity to open messenger
    public void openMessengerActivity() {
        Intent intent = new Intent(this, MessageTabsActivity.class);
        startActivity(intent);
    } // activity to open profile

    public void openMyProfileActivity() {
        Intent intentProfile = new Intent(this, ProfileActivity.class);
        startActivity(intentProfile);
    } // activity to open my lessons

    public void openMyLessonsActivity() {
        Intent intentLessons = new Intent(this, EventActivity.class);
        startActivity(intentLessons);
    }

    public void openMyTutorsActivity() {
        Intent intentTutors = new Intent(this, MyTutorsActivity.class);
        startActivity(intentTutors);
    }

    public void openFindTutorsActivity() {
        Intent intentFindTutors = new Intent(this, MapsActivity.class);
        startActivity(intentFindTutors);
    }

    public void openConnectActivity() {
        Intent intentConnect = new Intent(this, ConnectActivity.class);
        startActivity(intentConnect);
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            loginRedirect();
        }
    }

    private void loginRedirect(){
        Intent startIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }
}




