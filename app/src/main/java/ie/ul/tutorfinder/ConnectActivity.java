package ie.ul.tutorfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConnectActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private RecyclerView mUserList;
    private DatabaseReference mUserDataRef;

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

    private void addActionBar(){
        mToolbar = findViewById(R.id.connect_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Tutor Finder - Connect");
    }

    private void loginRedirect(){
        Intent startIntent = new Intent(ConnectActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        addActionBar();

        mUserDataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mUserList = findViewById(R.id.userListRView);
        mUserList.setHasFixedSize(true);
        mUserList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(mUserDataRef, User.class)
                        .build();

        FirebaseRecyclerAdapter<User, userViewContainer> firebaseRecyclerAdapter;
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User,userViewContainer>(options) {

            @Override
            protected void onBindViewHolder(@NonNull userViewContainer userViewContainer, int i, @NonNull User user) {
                userViewContainer.setName(user.getName());

                final String user_id = getRef(i).getKey();

                userViewContainer.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent otherProfileIntent = new Intent(ConnectActivity.this, OtherProfileActivity.class);
                        otherProfileIntent.putExtra("user_id", user_id);
                        startActivity(otherProfileIntent);
                    }
                });
            }

            @NonNull
            @Override
            public userViewContainer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_connect_layout, parent, false);

                return new userViewContainer(mView);
            }
        };

        firebaseRecyclerAdapter.startListening();
        mUserList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class userViewContainer extends RecyclerView.ViewHolder{

        View mView;

        public userViewContainer(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String name){
            TextView mName = mView.findViewById(R.id.userConnectName);
            mName.setText(name);
        }
    }
}
