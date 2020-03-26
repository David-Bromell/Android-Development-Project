package ie.ul.tutorfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

import java.text.Collator;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }

    public void onClickSignIn(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.idpConfig.EmailBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailibleProviders( providers )
                        .build(), RC_SIGN_IN );

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == RC_SIGN_IN) {
            idpResponse response = idpResponse.fromResultIntent( data );

            if (resultCode == RESULT_OK) {
                FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                System.out.println( "Sign in Successful! \n" +
                        "name = " + user.getDisplayName() + "\n" +
                        "email = " + user.getEmail() + "\n" +
                        "id = " + user.getUid() );
            } else {
                if (response == null) {
                    System.out.println( "Sign in cancelled" );
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    System.out.println( "No internet conncection" );
                    return;
                }

            }

        }

    }
}
