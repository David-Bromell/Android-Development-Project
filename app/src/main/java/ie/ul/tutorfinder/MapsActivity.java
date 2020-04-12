package ie.ul.tutorfinder;

import android.os.Bundle;
import android.util.Log;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleMap mMap2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );



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

    public double  returnTheLongitude(){

       double c1 = 0;
        return c1;
    }
    public double  returnTheLatitude(){

        double c2 = 0;
        return c2;
    }
    @Override
    public  void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap2 = googleMap;


        // Add a marker in csis and move the camera
        LatLng CSIS = new LatLng(returnTheLongitude(),returnTheLatitude());
        mMap.addMarker( new MarkerOptions().position( CSIS ).title( "Marker in CSIS" ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLng( CSIS ) );

        LatLng Kemmy = new LatLng( 52.672567, -8.576747);
        mMap2.addMarker( new MarkerOptions().position( Kemmy ).title( "Marker in Kemmy" ) );
        mMap2.moveCamera( CameraUpdateFactory.newLatLng( Kemmy ) );

    }

}
