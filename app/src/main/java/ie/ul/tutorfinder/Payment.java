package ie.ul.tutorfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class Payment extends AppCompatActivity {


    private Toolbar mToolbar;



    private static final int PAYPAL_REQUEST_CODE = 7777;

    //CREATES SANDBOX ENVRIONMENT FOR PAYPAL AND FETCHES TEST KEY FROM CONFIG.CLASS
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    //PAY BUTTON
    Button paypalPaymentBtn;
    //TEXT BOX FOR AMOUNT TO PAY
    EditText enterAmt;
    //STRING CREATED TO HOLD AMOUNT
    String amount = "";


    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

//CREATES ACTION BAR AT TOP OF APP SCREEN TO ENABLE EAST LOGOUT BUTTON ACCESS
    private void addActionBar() {
        mToolbar = findViewById(R.id.payment_page_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Tutor Finder - Payment");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        //FIREBASE LOGOUT FUNCTION AND DEDIRECTS TO LOGIN PAGE
        if (item.getItemId() == R.id.main_logout_button) {
            FirebaseAuth.getInstance().signOut();
            loginRedirect();
        }

        return true;
    }

    //FUNCTION TO REDIRECT USER TO LOGIN PAGE AFTER LOGOUT IS CLICKED
    private void loginRedirect() {
        Intent startIntent = new Intent(Payment.this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //CREATES NEW INTENT FOR PAYPALSERVICE AS SPECIFIED BY DOCUMENTATION
        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);
        //FINDS PAYPAL PAYMENT BUTTON IN LAYOUT
        paypalPaymentBtn = findViewById(R.id.paypalPaymentBtn);
        //FIND AMOUNT BOX IN LAYOUT
        enterAmt = findViewById(R.id.enterAmt);
        //CREATES LISTENER FOR PAY BUTTON
        paypalPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }

    //STARTS PAYMENT ON PAY BUTTON BEING CLICKED
    private void startPayment() {
        //GETS AMOUNT AS IN ENTERED BY USER
        amount = enterAmt.getText().toString();
        //PAYPAL CONVERTS THIS TO BIG DECIMAL AND HANDLES IT AS EURO,
        //THE CURRENCY CAN BE CHANGED HERE
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"EUR",
                //SHOWS USER WHAT THEY'RE PAYING FOR AT THE TOP OF THE SCREEN
                "Pay for tutor services", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent = intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    //ERROR HANDLING FOR PAYMENT HOUSED HERE
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //CHECKS THE REQUEST CODE IS VALID
        if (requestCode == PAYPAL_REQUEST_CODE){
            //CHECKS RESULT CODES OK
            if (resultCode == RESULT_OK){
                //CREATES PAYMENT CONFIRMATION CALLED CONF AS AN EXTRA MEASURE TO CHECK PAYMENT IS VALID
                PaymentConfirmation conf = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                //IF THIS CONFIRMATION IS NOT EMPTY THE BELOW CODE RUNS
                if (conf != null){

                    try {
                        //USERS DETAILS CREATED TO A JSONOBJECT STRING
                        String Details = conf.toJSONObject().toString(2);

                        startActivity(new Intent(this, PaypalPaymentDetails.class)
                                //INSERTS EXTRA PAYMENT INFO FIELD
                                .putExtra("Payment Information",Details)
                                //INSERTS EXTRA AMOUNT FIELD
                                .putExtra("Amount",amount));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                //IF RESULT CODE NOT VALID OR TRANSACTION IS CANCELLED BY USER TOAST MESSAGE DISPLAYED
            } else if (resultCode == Activity.RESULT_CANCELED)
                //MESSAGE TO DISPLAY IF CANCELLED
                Toast.makeText(this, "This Transaction has been cancelled, you will not be charged", Toast.LENGTH_LONG).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            //MESSAGE TO DISPLAY IF DETAILS ARE INVALID
            Toast.makeText(this, "Invalid details, please try again", Toast.LENGTH_LONG).show();
    }
}


































