package ie.ul.tutorfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private void addActionBar(){
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setTitle("Tutor Finder - Home");
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
        Intent startIntent = new Intent(EventActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_event );

        addActionBar();

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add( Calendar.YEAR, 1 );

        CalendarPickerView datePicker = findViewById( R.id.calender );
        datePicker.init( today, nextYear.getTime() ).withSelectedDate( today );
        datePicker.setOnDateSelectedListener( new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
            String selectedDate= DateFormat.getDateInstance(DateFormat.FULL).format(date);
                Toast.makeText( EventActivity.this, selectedDate, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onDateUnselected(Date date) {

                Calendar cal = Calendar.getInstance();

                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");

                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", true);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", "A Test Event from android app");
                startActivity(intent);
            }
        } );
    }
}