package ie.ul.tutorfinder;

        import android.app.DatePickerDialog;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.Toast;
        import android.widget.Toolbar;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.AppCompatSpinner;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.firestore.FirebaseFirestore;

        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    Button login;
    Toolbar toolbar;
    EditText email, password, name, birthdate, phone, Longitude, Latitude;
    AppCompatSpinner userType;
    Button signup;
    //ProgressBar progressBar;


    FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MainActivity";
    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Object String;

    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser() != null){

        }
    }

    public boolean isPhoneValid(String phone){
        return phone.length() == 10;
    }
    public  boolean ageValidation (String dateString) throws Exception{
        dateString = birthdate.toString();
        boolean ageVerification = true;
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
        return (calendar.getTime().after(date));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        email = findViewById( R.id.etEmail );
        password = findViewById( R.id.etPassword );
        signup = findViewById( R.id.btnSignup );
        login = findViewById( R.id.btnLogin );
        name = findViewById(R.id.etFullName);
        birthdate = findViewById(R.id.etBirthdate);
        phone = findViewById(R.id.etMobile);
        Longitude = findViewById(R.id.etLongitude);
        Latitude = findViewById(R.id.etLatitude);
        userType = findViewById(R.id.spinnerUserType);
        mDisplayDate = (EditText) findViewById(R.id.etBirthdate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Widget_Material_DatePicker,
                        mDateSetListener,
                        year,
                        month,
                        day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;

                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        firebaseAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                    if(!isPhoneValid(phone.getText().toString())){
                            Toast.makeText( MainActivity.this, "Invalid Phone number!", Toast.LENGTH_LONG ).show();
                        }


                    else{


                        firebaseAuth.createUserWithEmailAndPassword( email.getText().toString(), password.getText().toString() )
                                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            String Long, Lat;

                                            if(Longitude.getText().toString().isEmpty()) {
                                                Long = "-8.5869449";
                                            }
                                            else{
                                                Long = Longitude.getText().toString();
                                            }

                                            if(Latitude.getText().toString().isEmpty()){
                                                Lat = "52.6771541";
                                            }
                                            else{
                                                Lat = Latitude.getText().toString();
                                            }

                                            User user = new User(
                                                    name.getText().toString(),
                                                    email.getText().toString(),
                                                    phone.getText().toString(),
                                                    userType.getSelectedItem().toString(),
                                                    birthdate.getText().toString(),
                                                    Long,
                                                    Lat
                                            );

                                            FirebaseDatabase.getInstance()
                                                    .getReference("Users")
                                                    .child(FirebaseAuth
                                                            .getInstance()
                                                            .getCurrentUser()
                                                            .getUid()
                                                    ).setValue(user);
                                            // progressBar.setVisibility(View.GONE);
                                            Toast.makeText( MainActivity.this, "registered successfully!", Toast.LENGTH_LONG ).show();
                                        } else {
                                            Toast.makeText( MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG ).show();
                                        }
                                    }
                                });

                    }


            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(MainActivity.this, LoginActivity.class));
            }
        } );


    }

      
        

}






