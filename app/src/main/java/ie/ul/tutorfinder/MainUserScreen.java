package ie.ul.tutorfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainUserScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_screen);

        Intent intent = getIntent();
        //String name = intent.getStringExtra(MainActivity.EXTRA_NAME);

        TextView textView = findViewById(R.id.textView);
        //textView.setText(name);
    }
}
