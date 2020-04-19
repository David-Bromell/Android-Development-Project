package ie.ul.tutorfinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    Toolbar toolbar;
    EditText userPass;
    EditText userEmail;
    Button userLogin;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        userEmail = findViewById( R.id.etUserEmail );
        userPass = findViewById( R.id.etUserPass );
        userLogin = findViewById( R.id.btnUserLogin );
        firebaseAuth = FirebaseAuth.getInstance();

        userLogin.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                firebaseAuth.signInWithEmailAndPassword( userEmail.getText().toString(),
                        userPass.getText().toString() )
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity( new Intent( LoginActivity.this, MainUserScreen.class ) );
                                    //FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                   // Intent profileIntent = new Intent (LoginActivity.this, ProfileActivity.class);
                                   // profileIntent.putExtra("uid", currentUser.getUid());

                                } else {
                                    Toast.makeText( LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG ).show();
                                }

                            }
                        } );
            }

        } );
    }
}


