package ie.ul.tutorfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainUserScreen extends AppCompatActivity {

    private Button MyMessagesBtn;
    private Button MyProfileBtn1;
    private Button MyLessonsBtn1;
    private Button MyTutorsBtn1;
    private Button FindTutorBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_screen);

        Intent intent = getIntent();
        //String name = intent.getStringExtra(MainActivity.EXTRA_NAME);

        TextView textView = findViewById(R.id.textView);
        //textView.setText(name);
        //Link to message button
        MyMessagesBtn = findViewById(R.id.MyMessagesBtn);
        MyMessagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMessengerActivity();

            }
        });
        //link to profile button

        MyProfileBtn1 = findViewById(R.id.MyProfileBtn);
        MyProfileBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfileActivity();
            }

        });

        MyLessonsBtn1 = findViewById(R.id.MyLessonsBtn);
        MyLessonsBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyLessonsActivity();
            }

        });

        MyTutorsBtn1 = findViewById(R.id.MyTutorsBtn);
        MyTutorsBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyTutorsActivity();
            }

        });

        FindTutorBtn1 = findViewById(R.id.FindTutorsBtn);
        FindTutorBtn1.setOnClickListener(new View.OnClickListener() {
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
        Intent intentLessons = new Intent(this, MyLessonsActivity.class);
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



}




