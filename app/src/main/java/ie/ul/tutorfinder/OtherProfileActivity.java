package ie.ul.tutorfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OtherProfileActivity extends AppCompatActivity {

    private ImageView mProfileImage;
    private TextView mName, mEmail, mFriendsCount;
    private Button mSendRequestbtn;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        String user_id = getIntent().getStringExtra("user_id");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        mProfileImage = findViewById(R.id.profileImage);
        mName = findViewById(R.id.profileName);
        mEmail = findViewById(R.id.profileEmail);
        mFriendsCount = findViewById(R.id.profileFriendsCount);
        mSendRequestbtn = findViewById(R.id.sendRequestbtn);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String display_name = dataSnapshot.child("name").getValue(String.class);
                String display_email = dataSnapshot.child("email").getValue(String.class);

                mName.setText(display_name);
                mEmail.setText(display_email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
