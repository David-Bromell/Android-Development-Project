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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class StartActivity extends AppCompatActivity {
    Button login;
    EditText email, password, name, birthdate, phone, address ;
    AppCompatSpinner userType;
    Button signUp;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "StartActivity";
    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            //Purpose?
        }
    }

    public boolean isPhoneValid(String phone){
        return phone.length() == 10;
    }
    public  boolean ageValidation() throws Exception{
        String dateString = birthdate.toString();
        boolean ageVerification = true;
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
        return (calendar.getTime().after(date));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_start );

        email = findViewById( R.id.etEmail );
        password = findViewById( R.id.etPassword );
        signUp = findViewById( R.id.btnSignup );
        login = findViewById( R.id.btnLogin );
        name = findViewById(R.id.etFullName);
        birthdate = findViewById(R.id.etBirthdate);
        phone = findViewById(R.id.etMobile);
        address = findViewById( R.id.etAddress );
        userType = findViewById(R.id.spinnerUserType);
        mDisplayDate = findViewById(R.id.etBirthdate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        StartActivity.this,
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
        signUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isPhoneValid(phone.getText().toString())){
                    Toast.makeText( StartActivity.this, "Invalid Phone number!", Toast.LENGTH_LONG ).show();
                }

                else{
                    firebaseAuth.createUserWithEmailAndPassword( email.getText().toString(), password.getText().toString() )
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            //String Long, Lat;

                                           // if(Longitude.getText().toString().isEmpty()) {
                                               // Long = "-8.5869449";
                                           // }
                                           // else{
                                                //Long = Longitude.getText().toString();
                                            //}

                                            //if(Latitude.getText().toString().isEmpty()){
                                           //    Lat = "52.6771541";
                                           // }
                                            //else{
                                               // Lat = Latitude.getText().toString();
                                           // }

                                            User user = new User(
                                                    name.getText().toString(),
                                                    email.getText().toString(),
                                                    phone.getText().toString(),
                                                    userType.getSelectedItem().toString(),
                                                    birthdate.getText().toString(),
                                                    address.getText().toString()


                                            );

                                            FirebaseDatabase.getInstance()
                                                    .getReference("Users")
                                                    .child(FirebaseAuth
                                                            .getInstance()
                                                            .getCurrentUser()
                                                            .getUid()
                                                    ).setValue(user);
                                            Toast.makeText( StartActivity.this, "Registered Successfully! Please Login!", Toast.LENGTH_LONG ).show();
                                            Intent mIntent = new Intent(StartActivity.this, LoginActivity.class);
                                            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(mIntent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText( StartActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG ).show();
                                        }
                                    }
                            });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(StartActivity.this, LoginActivity.class));
            }
        } );
    }
}






