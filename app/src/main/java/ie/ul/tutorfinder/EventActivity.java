package ie.ul.tutorfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.EventLog;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_event );

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

            }

        } );
    }
}