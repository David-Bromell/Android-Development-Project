package ie.ul.tutorfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestsActivity extends AppCompatActivity {
    public TextView requestTextView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    List<String> nameList, requestList;
    String uid;
    ArrayAdapter<String> adapter;
    DatabaseReference databaseReference;
    private RecyclerView mRequestList;

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private RecyclerView mUserList;
    private DatabaseReference mUserDataRef;



    private void addActionBar() {
        mToolbar = findViewById(R.id.connect_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Tutor Finder - Requests");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu( menu );

        getMenuInflater().inflate( R.menu.main_menu, menu );


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected( item );

        if (item.getItemId() == R.id.main_logout_button) {
            FirebaseAuth.getInstance().signOut();
            loginRedirect();
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_requests );
        uid = user.getUid();
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String current_userID = current_user.getUid();
        addActionBar();
        mUserDataRef = FirebaseDatabase.getInstance().getReference().child("friend_requests").child(current_userID).child( "request_type" );

        mUserList = findViewById(R.id.userListRView);
        mUserList.setHasFixedSize(true);
        mUserList.setLayoutManager(new LinearLayoutManager(this));


        //userRef.keepSynced( true );

        mRequestList = (RecyclerView) findViewById( R.id.request_list );
        mRequestList.setHasFixedSize( true );
        mRequestList.setLayoutManager( new LinearLayoutManager( this ) );



        mUserDataRef.addValueEventListener( new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    @Override
    protected void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(mUserDataRef, User.class)
                        .build();

        FirebaseRecyclerAdapter<User, RequestsActivity.userViewContainer> firebaseRecyclerAdapter;
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, RequestsActivity.userViewContainer>(options) {


            @Override
            protected void onBindViewHolder(@NonNull RequestsActivity.userViewContainer userViewContainer, int i, @NonNull User user) {
                userViewContainer.setName(user.getName());

                final String user_id = getRef(i).getKey();

                userViewContainer.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent otherProfileIntent = new Intent(RequestsActivity.this, OtherProfileActivity.class);
                        otherProfileIntent.putExtra("user_id", user_id);
                        startActivity(otherProfileIntent);
                    }
                });
            }

            @NonNull
            @Override
            public RequestsActivity.userViewContainer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_connect_layout, parent, false);

                return new RequestsActivity.userViewContainer(mView);
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

    private void loginRedirect() {
        Intent startIntent = new Intent( RequestsActivity.this, LoginActivity.class );
        startActivity( startIntent );
        finish();

    }
}




