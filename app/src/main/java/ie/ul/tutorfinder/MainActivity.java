package ie.ul.tutorfinder;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import android.widget.Toolbar;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    Button login;
    Toolbar toolbar;
    EditText email;
    EditText password;
    Button signup;


    FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        email = findViewById( R.id.etEmail );
        password = findViewById( R.id.etPassword );
        signup = findViewById( R.id.btnSignup );
        login = findViewById( R.id.btnLogin );


        firebaseAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.createUserWithEmailAndPassword( email.getText().toString(), password.getText().toString() )
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText( MainActivity.this, "registered successfully!", Toast.LENGTH_LONG ).show();
                                } else {
                                    Toast.makeText( MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG ).show();

                                }
                            }
                        } );
            }
        } );

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(MainActivity.this, LoginActivity.class));
            }
        } );


    }
}





