package ie.ul.tutorfinder;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class tutorFinder extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled( true );
        //enable offline capabilities
    }
}
