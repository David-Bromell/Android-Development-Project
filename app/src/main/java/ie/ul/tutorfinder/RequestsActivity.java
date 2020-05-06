package ie.ul.tutorfinder;

import android.content.Intent;
import android.os.Bundle;
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


    private Toolbar mToolbar;

    private void addActionBar() {
        mToolbar = findViewById( R.id.requests_page_toolbar );
        setSupportActionBar( mToolbar );
        mToolbar.setTitle( "Tutor Finder - Connect Requests" );
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

        addActionBar();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String currentUserId = mAuth.getCurrentUser().getUid();
        //userRef.keepSynced( true );

        mRequestList = (RecyclerView) findViewById( R.id.request_list );
        mRequestList.setHasFixedSize( true );
        mRequestList.setLayoutManager( new LinearLayoutManager( this ) );

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        nameList = new ArrayList<>();
        requestList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child( "friend_requests" );

        databaseReference.addValueEventListener( new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameList.clear();
                requestList.clear();

                String name, request;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    name = ds.child( uid ).getValue( String.class );
                    request = ds.child( "request_type" ).getValue( String.class );
                    nameList.add( name );
                    requestList.add( request );
                    for (int i = 0; i < nameList.size(); i++) {
                        requestTextView.setText( nameList.get( i ) );


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    @Override
    protected void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<friend_requests> options =
                new FirebaseRecyclerOptions.Builder<friend_requests>()
                        .setQuery(databaseReference, friend_requests.class)
                        .build();

        FirebaseRecyclerAdapter<friend_requests, RequestViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<friend_requests, RequestViewHolder>(options){






            @Override
            protected void onBindViewHolder(@NonNull RequestViewHolder requestViewHolder, int i, @NonNull friend_requests friend_requests) {

            }

            @NonNull
            @Override
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };

    }


    public class RequestViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public RequestViewHolder(@NonNull View itemView) {

            super( itemView );
            mView = itemView;
        }

    }

    private void loginRedirect() {
        Intent startIntent = new Intent( RequestsActivity.this, LoginActivity.class );
        startActivity( startIntent );
        finish();

    }
}




