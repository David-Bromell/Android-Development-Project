package ie.ul.tutorfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_screen);

        mAuth = FirebaseAuth.getInstance();

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

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Intent startIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(startIntent);
            finish();
        }
    }
}




