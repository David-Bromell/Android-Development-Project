package ie.ul.tutorfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainUserScreen extends AppCompatActivity {

    private Button MyMessagesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_screen);

        Intent intent = getIntent();
        //String name = intent.getStringExtra(MainActivity.EXTRA_NAME);

        TextView textView = findViewById(R.id.textView);
        //textView.setText(name);
        MyMessagesBtn = findViewById(R.id.MyMessagesBtn);
        MyMessagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMessangerActivity();

            }
        });

    }

    public void openMessangerActivity(){
        Intent intent = new Intent(this, MessangerActivity.class);
        startActivity(intent);
    }
}
