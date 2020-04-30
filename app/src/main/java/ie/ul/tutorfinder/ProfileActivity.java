package ie.ul.tutorfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {

    private TextView FirstNameTextView, EmailTextView, PhoneTextView, BirthDateTextView;
    ImageView profileImage;
    int TAKE_IMAGE_CODE = 10001;
    public static final String TAG = "TAG";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toolbar mToolbar;

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

    private void loginRedirect(){
        Intent startIntent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void openMyTutorsActivity() {
        Intent intentTutors = new Intent( this, MyTutorsActivity.class );
        startActivity( intentTutors );
    }

    public void openMyLessonsActivity() {
        Intent intentLessons = new Intent( this, EventActivity.class );
        startActivity( intentLessons );
    }

    public void openMyRequestsActivity() {
        Intent intentRequests = new Intent( this, RequestsActivity.class );
        startActivity( intentRequests );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );

        addActionBar();

        Button logOut = findViewById(R.id.LogoutBtn);
        Button myTutors = findViewById(R.id.myTutotrsbtn);
        Button myLessons = findViewById(R.id.myLessonsbtn);
        Button myRequests = findViewById(R.id.requestsBtn);
        Button paymentBtn = findViewById(R.id.paymentBtn);
        Button mySettings = findViewById(R.id.mySettingsbtn);

        myLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyLessonsActivity();
            }
        } );
        myTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyTutorsActivity();
            }
        } );
        myRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyRequestsActivity();
            }
        });
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaymentActivity();
            }

        } );
        mySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMySettingsActivity();
            }
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        FirstNameTextView = findViewById( R.id.first_Name_TextView );
        EmailTextView = findViewById( R.id.email_TextView );
        PhoneTextView = findViewById( R.id.phone_TextView );
        BirthDateTextView = findViewById( R.id.birthDate_TextView );
        profileImage = findViewById( R.id.profileImageView );

        if(user!=null){
            Glide.with( this ).load(user.getPhotoUrl()).into(profileImage);
        }

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String userName = dataSnapshot.child( "name" ).getValue( String.class );
                    String email = dataSnapshot.child( "email" ).getValue( String.class );
                    String birthdate = dataSnapshot.child( "birthdate" ).getValue( String.class );
                    String phone = dataSnapshot.child( "phone" ).getValue( String.class );

                    FirstNameTextView.setText( "Welcome to your profile, " + userName + "!" );
                    EmailTextView.setText( "EMAIL: " + email );
                    PhoneTextView.setText( "CONTACT NUM: " + phone );
                    BirthDateTextView.setText( "DOB: " + birthdate );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        logOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent( ProfileActivity.this, MainActivity.class ) );
            }
        } );
    }

    private void openPaymentActivity() {
        Intent intent = new Intent( this, Payment.class );
        startActivity( intent );
    }

    private void openMySettingsActivity() {
        Intent intent = new Intent( this, Payment.class );
        startActivity( intent );
    }

    public void handleImageClick(View view) {
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        if (intent.resolveActivity( getPackageManager() ) != null) {
            startActivityForResult( intent, TAKE_IMAGE_CODE );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == TAKE_IMAGE_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitnmap = (Bitmap) data.getExtras().get( "data" );
                    profileImage.setImageBitmap( bitnmap );
                    handleUpload( bitnmap );
            }
        }
    }

    private void handleUpload(Bitmap bitnmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitnmap.compress( Bitmap.CompressFormat.JPEG, 100, baos );
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference().child( "profileImages" ).child( uid + ".jpeg" );

        reference.putBytes( baos.toByteArray() )
                .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    getDownloadUrl( reference );
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e( TAG, "onFailure: ", e.getCause() );
                    }
                } );
    }

    private void getDownloadUrl (StorageReference reference){
        reference.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
            Log.d( TAG, "OnSuccess:"+ uri );
            setUserProfileUrl( uri );
            }
        } );
    }

    private void setUserProfileUrl(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        user.updateProfile( request ).addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText( ProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( ProfileActivity.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
            }
        } );
    }
}
