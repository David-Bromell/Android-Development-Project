package ie.ul.tutorfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {

    private TextView FirstNameTextiView, EmailTextView, PhoneTextView, BirthDateTextView;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private FirebaseAuth mauth;
    private String currentUserId;
    private Button paymentBtn;
    private Button logOut;
    private Button myTutors;
    private Button myLessons;

    public void openMyTutorsActivity() {
        Intent intentTutors = new Intent(this, MyTutorsActivity.class);
        startActivity(intentTutors);
    }

    public void openMyLessonsActivity() {
        Intent intentLessons = new Intent(this, MyLessonsActivity.class);
        startActivity(intentLessons);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logOut = findViewById(R.id.LogoutBtn);
        myTutors = findViewById(R.id.myTutotrsbtn);
        myLessons = findViewById(R.id.myLessonsbtn);

        myLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyLessonsActivity();
            }
        });

        myTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyTutorsActivity();
            }
        });

        mauth = FirebaseAuth.getInstance();
        currentUserId = mauth.getCurrentUser().getUid();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        paymentBtn = findViewById(R.id.paymentBtn);
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaymentActivity();
            }

        });

        FirstNameTextiView = findViewById(R.id.first_Name_TextView);
        EmailTextView = findViewById(R.id.email_TextView);
        PhoneTextView = findViewById(R.id.phone_TextView);
        BirthDateTextView = findViewById(R.id.birthDate_TextView);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String userName = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String birthdate = dataSnapshot.child("birthdate").getValue(String.class);
                    String phone = dataSnapshot.child("phone").getValue(String.class);

                    FirstNameTextiView.setText("Welcome to your profile, " + userName + "!");
                    EmailTextView.setText("EMAIL: " + email);
                    PhoneTextView.setText("CONTACT NUM: " + phone);
                    BirthDateTextView.setText("DOB: " + birthdate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(ProfileActivity.this, MainActivity.class));
            }
        });
    }

    private void openPaymentActivity() {
        Intent intent = new Intent(this,Payment.class);
        startActivity(intent);
    }
}
