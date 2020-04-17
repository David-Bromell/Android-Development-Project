package ie.ul.tutorfinder;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    ListView ll;
    private GoogleMap mMap;
    private GoogleMap mMap2;
    DatabaseReference databaseReference;
    FirebaseUser user;
    List<String> itemlist;



    ArrayAdapter<String>adapter;



   // itemlist = new ArrayList<>();

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
        ll=(ListView)findViewById( R.id.listView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        itemlist = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener( new ValueEventListener() {


            @Override

    public  void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        itemlist.clear();
        String latitude = dataSnapshot.child(uid).child("Longitude").getValue(String.class);
        String longitude = dataSnapshot.child( uid ).child( "Latitude" ).getValue( String.class );

        itemlist.add(latitude);
        itemlist.add(longitude);
        Log.d("YEEEEEET",itemlist.get(0));





    }










    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
} );


        }



/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
 */

// Read from the database
public  String returnTheLongitude() {

        String c1 = itemlist.get(1);
        c1.toString();
        return c1;
        }

public String returnTheLatitude() {

        String c2 = itemlist.get(0);
        c2.toString();
        return c2;
        }

@Override
public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap2 = googleMap;


        // Add a marker in csis and move the camera
        LatLng CSIS = new LatLng(Double.parseDouble(returnTheLongitude()),((Double.parseDouble(returnTheLatitude()))));
        mMap.addMarker( new MarkerOptions().position( CSIS ).title( "Marker in CSIS" ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLng( CSIS ) );

        LatLng Kemmy = new LatLng( 52.672567, -8.576747 );
        mMap2.addMarker( new MarkerOptions().position( Kemmy ).title( "Marker in Kemmy" ) );
        mMap2.moveCamera( CameraUpdateFactory.newLatLng( Kemmy ) );

        }
        }

