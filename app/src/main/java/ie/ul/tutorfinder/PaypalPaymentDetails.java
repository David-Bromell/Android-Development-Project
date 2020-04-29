package ie.ul.tutorfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

//PAYMENT DETAILS FOR PAYPAL USERS
public class PaypalPaymentDetails extends AppCompatActivity {
    //SHOWS ID, PRICE AND CURRENT POSITION OF PAYMENT (I.E PAYMENT STATUS)
    TextView Id,Amount,condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        //SETS ID TO LAYOUT ID IN ACTIVITY_PAYMENT_DETAILS
        Id = findViewById(R.id.Id);
        //SETS AMOUNT TO LAYOUT ID IN ACTIVITY_PAYMENT_DETAILS
        Amount = findViewById(R.id.Amount);
        //SETS CONDITION TO LAYOUT ID IN ACTIVITY_PAYMENT_DETAILS
        condition = findViewById(R.id.condition);

        Intent intent = getIntent();
        //TRY CATCH FOR ERROR HANDLING WITHIN PAYMENT
        try {
            //CREATES JSON OBJECT FOR PAYMENT DETAILS
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            //SHOWS PAYMENT DETAILS AS CREATED ABOVE VIA INTENT
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //USED TO DISPLAY DETAILS CREATED ABOVE IN JSONOBJECT
    private void showDetails(JSONObject response, String paymentAmount) {
        //TRY CATCH FOR ERROR HANDLING
        try {
            //SETS ID, CONDITION AND CONDITION/OF PAYMENT
            Id.setText(response.getString("id"));
            condition.setText(response.getString("state"));
            Amount.setText("€"+paymentAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}