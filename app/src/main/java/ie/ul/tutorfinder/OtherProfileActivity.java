package ie.ul.tutorfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.internal.$Gson$Preconditions;

public class OtherProfileActivity extends AppCompatActivity {

    private ImageView mProfileImage;
    private TextView mName, mEmail, mFriendsCount;
    private Button mSendRequestbtn;
    private DatabaseReference mDatabaseReference;
    private String current_state;
    private DatabaseReference friendReqRef;
    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        final String user_id = getIntent().getStringExtra("user_id");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        friendReqRef = FirebaseDatabase.getInstance().getReference().child("friend_requests");
        current_user = FirebaseAuth.getInstance().getCurrentUser();

        mProfileImage = findViewById(R.id.profileImage);
        mName = findViewById(R.id.profileName);
        mEmail = findViewById(R.id.profileEmail);
        mFriendsCount = findViewById(R.id.profileFriendsCount);
        mSendRequestbtn = findViewById(R.id.sendRequestbtn);

        current_state = "not_friends";

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

        mSendRequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSendRequestbtn.setEnabled(false);

                //Not Friends Processing

                if(current_state.equals("not_friends")){
                    friendReqRef.child(current_user.getUid()).child(user_id)
                            .child("request_type").setValue("sent")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                friendReqRef.child(user_id).child(current_user.getUid())
                                        .child("request_type").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mSendRequestbtn.setEnabled(true);
                                        current_state = "request_sent";
                                        mSendRequestbtn.setText("Cancel Request");
                                        Toast.makeText(OtherProfileActivity.this,"Request Sent!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Toast.makeText(OtherProfileActivity.this,
                                            "Failed to send request!",
                                                Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

                //Cancel Request State
                if(current_state.equals("request_sent")){
                    friendReqRef.child(current_user.getUid()).child(user_id).removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            friendReqRef.child(user_id).child(current_user.getUid()).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            mSendRequestbtn.setEnabled(true);
                                            current_state="not_friends";
                                            mSendRequestbtn.setText("Connect");
                                        }
                                    });
                                    Toast.makeText(OtherProfileActivity.this, "Request Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
