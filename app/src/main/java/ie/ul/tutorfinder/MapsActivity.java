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

/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    ListView ll;
    private GoogleMap mMap;
    DatabaseReference databaseReference;
    FirebaseUser user;
    List<String> nameList, latitudeList, longitudeList;
    String uid;
    ArrayAdapter<String>adapter;

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
        nameList = new ArrayList<>();
        latitudeList = new ArrayList<>();
        longitudeList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public  void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nameList.clear();
                latitudeList.clear();
                longitudeList.clear();

                String name, latitude, longitude;

                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    name = ds.child("name").getValue(String.class);
                    latitude = ds.child("latitude").getValue(String.class);
                    longitude = ds.child("longitude").getValue(String.class);
                    nameList.add(name);
                    latitudeList.add(latitude);
                    longitudeList.add(longitude);
                }

                updateMap();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //Method to update the map with users as markers
    public void updateMap(){

        LatLng newMarker;

        for(int i=0; i<nameList.size(); i++){

            newMarker = new LatLng(Double.parseDouble(latitudeList.get(i)),Double.parseDouble(longitudeList.get(i)));
            mMap.addMarker(new MarkerOptions().position(newMarker).title(nameList.get(i)));
            if(i==(nameList.size()-1)){
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newMarker));
            }
        }
    }

    public String returnTheLongitude() {

        return null; //itemList.get(1);
    }

    public String returnTheLatitude() {

        return null; //itemList.get(0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng Kemmy = new LatLng( 52.6771541, -8.5869449 );
        mMap.addMarker( new MarkerOptions().position( Kemmy ).title( "University of Limerick" ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLng( Kemmy ) );
    }
}

